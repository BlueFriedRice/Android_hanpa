package com.example.hanpa.Dictionary;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanpa.MainActivity;
import com.example.hanpa.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class pureKorFragment extends Fragment {

    RecyclerViewAdapter itemsAdapter;
    private ArrayList<RecyclerViewModel> word_list = new ArrayList<RecyclerViewModel>();
    MainActivity dic = new MainActivity();
    int lang = dic.getLang();
    String t_before = "";
    String t_after = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dic_fragment_layout,container, false);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (lang) {
            case 0:
                read_excel(0);
                break;

            case 1:
               read_excel(1);
                break;

            case 2:
                read_excel(2);
                break;

            case 3:
                read_excel(3);
                break;
        }

        itemsAdapter = new RecyclerViewAdapter(pureKorFragment.this.getActivity(), word_list, null);
        final RecyclerView clv = (RecyclerView) view.findViewById(R.id.Recycler);
        clv.setLayoutManager(new LinearLayoutManager(pureKorFragment.this.getActivity()));
        clv.setHasFixedSize(true);
        clv.setAdapter(itemsAdapter);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setQueryHint("Searching...");

        SearchView.SearchAutoComplete searchAutoComplete =
                (SearchView.SearchAutoComplete)searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.BLACK);
        searchAutoComplete.setTextColor(Color.BLACK);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemsAdapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu,inflater);
    }

    public void read_excel(int lang){
        ArrayList<String> word = new ArrayList<String>();
        ArrayList<String> mean = new ArrayList<String>();

        try {
            InputStream is = getActivity().getAssets().open("pure_word.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if(wb != null) {
                Sheet sheet1 = wb.getSheet(0);   // 시트 불러오기
                Sheet sheet2 = wb.getSheet(1);

                if(sheet1 != null) {
                    int colTotal = sheet1.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet1.getColumn(colTotal-1).length;

                    for(int row=rowIndexStart;row<rowTotal;row++) {
                        for(int col=0;col<colTotal;col++) {
                            String contents = sheet1.getCell(col, row).getContents();
                            if(col==0)
                                word.add(contents);
                            else if(col==1)
                                mean.add(contents);
                        }
                    }
                }

                if(sheet2 != null) {
                    int colTotal = sheet2.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet2.getColumn(colTotal-1).length;

                    for(int row=rowIndexStart;row<rowTotal;row++) {
                        for(int col=0;col<colTotal;col++) {
                            String contents = sheet2.getCell(col, row).getContents();
                            if(col==0)
                                word.add(contents);
                            else if(col==1)
                                mean.add(contents);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        for(int i=0;i<word.size();i++) {
            String trans ="";
            if(lang != 0) {
                t_before = mean.get(i);
                TranslateTask translateTask = new TranslateTask();
                translateTask.execute();
            }
            else
                t_after = mean.get(i);
            word_list.add(new RecyclerViewModel(word.get(i), t_after));
        }
    }

    class TranslateTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            String clientId = "bIzE5RYIMl5qISB0T6qc";//애플리케이션 클라이언트 아이디값";
            String clientSecret = "QkM7IiK5m8";//애플리케이션 클라이언트 시크릿값";
            try {
                String text = URLEncoder.encode(t_before, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                String target = "";
                switch (lang){
                    case 0:
                        target = "ko";
                        break;
                    case 1:
                        target = "en";
                        break;
                    case 2:
                        target = "zh-CN";
                        break;
                    case 3:
                        target = "ja";
                        break;
                }
                // post request
                String postParams = "source=ko&target=" + target + "&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                t_after = response.toString();
            } catch (Exception e) {
                Log.e("error", String.valueOf(e));
            }
            return null;
        }
    }
}
