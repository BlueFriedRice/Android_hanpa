package com.example.hanpa.Dictionary;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanpa.MainActivity;
import com.example.hanpa.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class abbFragment extends Fragment {

    RecyclerViewAdapter itemsAdapter;
    int lang;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dic_fragment_layout, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity dic = new MainActivity();
        lang = dic.getLang();

        final ArrayList<RecyclerViewModel> word_list = new ArrayList<RecyclerViewModel>();

        switch (lang) {
            case 0:
                ArrayList<String> word = new ArrayList<String>();
                ArrayList<String> mean = new ArrayList<String>();

                try {
                    InputStream is = getActivity().getAssets().open("word.xls");
                    Workbook wb = Workbook.getWorkbook(is);

                    if(wb != null) {
                        Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                        if(sheet != null) {
                            int colTotal = sheet.getColumns();    // 전체 컬럼
                            int rowIndexStart = 1;                  // row 인덱스 시작
                            int rowTotal = sheet.getColumn(colTotal-1).length;

                            for(int row=rowIndexStart;row<rowTotal;row++) {
                                for(int col=0;col<colTotal;col++) {
                                    String contents = sheet.getCell(col, row).getContents();
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
                for(int i=0;i<word.size();i++)
                word_list.add(new RecyclerViewModel(word.get(i), mean.get(i)));
                break;
            case 1:
                ArrayList<String> word2 = new ArrayList<String>();
                ArrayList<String> mean2 = new ArrayList<String>();
                try {
                    InputStream is = getActivity().getAssets().open("word.xls");
                    Workbook wb = Workbook.getWorkbook(is);

                    if(wb != null) {
                        Sheet sheet = wb.getSheet(1);   // 시트 불러오기
                        if(sheet != null) {
                            int colTotal = sheet.getColumns();    // 전체 컬럼
                            int rowIndexStart = 1;                  // row 인덱스 시작
                            int rowTotal = sheet.getColumn(colTotal-1).length;

                            for(int row=rowIndexStart;row<rowTotal;row++) {
                                for(int col=0;col<colTotal;col++) {
                                    String contents = sheet.getCell(col, row).getContents();
                                    if(col==0)
                                        word2.add(contents);
                                    else if(col==1)
                                        mean2.add(contents);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BiffException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<word2.size();i++)
                    word_list.add(new RecyclerViewModel(word2.get(i), mean2.get(i)));
                break;
            case 2:
                ArrayList<String> word3 = new ArrayList<String>();
                ArrayList<String> mean3 = new ArrayList<String>();
                try {
                    InputStream is = getActivity().getAssets().open("word.xls");
                    Workbook wb = Workbook.getWorkbook(is);

                    if(wb != null) {
                        Sheet sheet = wb.getSheet(2);   // 시트 불러오기
                        if(sheet != null) {
                            int colTotal = sheet.getColumns();    // 전체 컬럼
                            int rowIndexStart = 1;                  // row 인덱스 시작
                            int rowTotal = sheet.getColumn(colTotal-1).length;

                            for(int row=rowIndexStart;row<rowTotal;row++) {
                                for(int col=0;col<colTotal;col++) {
                                    String contents = sheet.getCell(col, row).getContents();
                                    if(col==0)
                                        word3.add(contents);
                                    else if(col==1)
                                        mean3.add(contents);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BiffException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<word3.size();i++)
                    word_list.add(new RecyclerViewModel(word3.get(i), mean3.get(i)));
                break;
            case 3:
                ArrayList<String> word4 = new ArrayList<String>();
                ArrayList<String> mean4 = new ArrayList<String>();
                try {
                    InputStream is = getActivity().getAssets().open("word.xls");
                    Workbook wb = Workbook.getWorkbook(is);

                    if(wb != null) {
                        Sheet sheet = wb.getSheet(3);   // 시트 불러오기
                        if(sheet != null) {
                            int colTotal = sheet.getColumns();    // 전체 컬럼
                            int rowIndexStart = 1;                  // row 인덱스 시작
                            int rowTotal = sheet.getColumn(colTotal-1).length;

                            for(int row=rowIndexStart;row<rowTotal;row++) {
                                for(int col=0;col<colTotal;col++) {
                                    String contents = sheet.getCell(col, row).getContents();
                                    if(col==0)
                                        word4.add(contents);
                                    else if(col==1)
                                        mean4.add(contents);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BiffException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<word4.size();i++)
                    word_list.add(new RecyclerViewModel(word4.get(i), mean4.get(i)));
                break;
        }

        itemsAdapter = new RecyclerViewAdapter(abbFragment.this.getActivity(), word_list, null);
        final RecyclerView clv = (RecyclerView) view.findViewById(R.id.Recycler);
        clv.setLayoutManager(new LinearLayoutManager(abbFragment.this.getActivity()));
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
}