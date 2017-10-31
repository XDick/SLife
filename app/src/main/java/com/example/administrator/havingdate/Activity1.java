package com.example.administrator.havingdate;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;






import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Activity1 extends Fragment {


    private SwipeRefreshLayout swipeRefresh;
    private Information[] informations ={new Information("麒麟大虾",R.drawable.qqdx),
            new Information("圣鲸国际美食百汇",R.drawable.sjgjmsbh),
            new Information("宴遇时尚餐厅",R.drawable.yyssct)};


    private List<Information> informationList = new ArrayList<>();

    private InformationAdapter adapter;






    /*--------------------------------------------------------*/
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_layout1, container,false);


/*-----------------------------列表--------------------------------------*/
         initInformations();
         RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
         GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
         recyclerView.setLayoutManager(layoutManager);
         adapter = new InformationAdapter(informationList);
         recyclerView.setAdapter(adapter);

         /*--------------------------实现刷新功能---------------------------*/
         swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
         swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
         swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
             @Override
             public void onRefresh(){
                 refreshInformations();
             }
         });


         return view;
     }






    private void refreshInformations() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ((AppCompatActivity) getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initInformations();
                        Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    /*------------------------------------------------------------------------*/


    private void initInformations(){
        informationList.clear();
        for(int i = 0;i<10;i++){
            Random random = new Random();
            int index = random.nextInt(informations.length);
            informationList.add(informations[index]);
        }
    }}



/*----------------------------------------------------------------------------*/




