package com.example.hanpa.Dictionary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanpa.MainActivity;
import com.example.hanpa.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable
{
    private static final String LOG_TAG = RecyclerViewAdapter.class.getSimpleName();
    private ArrayList<RecyclerViewModel> mItems;
    private ArrayList<RecyclerViewModel> searchItemListFull;
    ItemListener mListener;
    Context context;
    public static int position;
    AnimationDrawable signalAnimation;

    public RecyclerViewAdapter(Activity context, ArrayList<RecyclerViewModel> program, ItemListener listener)
    {

        this.context = context;
        mItems = program;
        mListener = listener;
        searchItemListFull = new ArrayList<>(program);

    }

    public void setOnItemClickListener(ItemListener listener)
    {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.dic_list_item, parent, false);
        context = parent.getContext();

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.setData(mItems.get(position));

    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return searchItemListFilter;
    }
    private Filter searchItemListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<RecyclerViewModel> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(searchItemListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (RecyclerViewModel model : searchItemListFull) {
                    if(model.getWord().toLowerCase().contains(filterPattern)) {
                        filteredList.add(model);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mItems.clear();
            mItems.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView cardView;
        public RecyclerViewModel dic_list;
        private TextView word, mean;
        private ImageButton dic_speaker;
        View textContainer;
        private TextToSpeech tts;
        int lang;

        public ViewHolder(View itemView) {

            super(itemView);

            tts = new TextToSpeech(itemView.getContext(), onTTSInitListener);

            dic_speaker = (ImageButton) itemView.findViewById(R.id.dic_speaker);
            dic_speaker.setBackgroundResource(R.drawable.speaker_animation);

            cardView = (CardView) itemView.findViewById(R.id.cvItem);
            word = (TextView) itemView.findViewById(R.id.word_text);
            mean = (TextView) itemView.findViewById(R.id.meaning_text);
            textContainer = itemView.findViewById(R.id.text_container);

            MainActivity dic = new MainActivity();
            lang = dic.getLang();

            dic_speaker.setTag(position);
            dic_speaker.setOnClickListener(this);
        }

        public void setData(RecyclerViewModel dic_list) {
            this.dic_list = dic_list;

            word.setText(dic_list.getWord());
            mean.setText(dic_list.getMeaning());
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(dic_list, getAdapterPosition());
            }
            switch (v.getId()) {

                case R.id.dic_speaker:
                        HashMap mTTSMap = new HashMap();
                        signalAnimation = (AnimationDrawable) dic_speaker.getBackground();
                        signalAnimation.start();
                        AudioManager audio = (AudioManager) v.getContext().getSystemService(Context.AUDIO_SERVICE);
                        int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                        audio.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0); // 최대볼륨

                        mTTSMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "unique_id");
                        tts.setPitch(1.0f);         // 음성 톤은 기본 설정
                        tts.setSpeechRate(0.9f);    // 읽는 속도를 0.9빠르기로 설정
//                        Toast.makeText(context,dic_list.getMeaning(), Toast.LENGTH_SHORT).show();
                        tts.speak(dic_list.getWord() + " 의 정확한 의미는 " + dic_list.getMeaning() + " 입니다.",
                                TextToSpeech.QUEUE_FLUSH, mTTSMap);
//                        Toast.makeText(context, dic_list.getWord()+lang, Toast.LENGTH_SHORT).show();
                    break;
            }


        }

        private TextToSpeech.OnInitListener onTTSInitListener = new TextToSpeech.OnInitListener() {

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            @Override
            public void onInit(int i) {

                if (i == TextToSpeech.SUCCESS) {

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        int result;

                        @Override
                        public void onStart(String s) {

                            switch (lang) {

                                case 0:
                                    result = tts.setLanguage(Locale.KOREAN);

                                    if (result == TextToSpeech.LANG_MISSING_DATA
                                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Log.e("TTS", "This Language is not supported");
                                    }

                                    break;

                                case 1:

                                    result = tts.setLanguage(Locale.ENGLISH);

                                    if (result == TextToSpeech.LANG_MISSING_DATA
                                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Log.e("TTS", "This Language is not supported");
                                    }
                                    break;

                                case 2:

                                    result = tts.setLanguage(Locale.CHINESE);

                                    if (result == TextToSpeech.LANG_MISSING_DATA
                                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Log.e("TTS", "This Language is not supported");
                                    }
                                    break;

                                case 3:

                                    result = tts.setLanguage(Locale.JAPANESE);

                                    if (result == TextToSpeech.LANG_MISSING_DATA
                                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Log.e("TTS", "This Language is not supported");
                                    }
                                    break;
                            }
                        }

                        @Override
                        public void onDone(String s) {
                            signalAnimation.stop();
                            dic_speaker.setImageResource(R.drawable.ani_speak3);
                        }

                        @Override
                        public void onError(String s) {

                        }
                    });

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }

        };

    }



    public interface ItemListener {
        void onItemClick(RecyclerViewModel pName, int position);

    }

}

