package com.psiarb.go.wordchai;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements MiscFragment.MiscFragmentListener, CardsFragment.CardsFragmentListener{

    private Typeface typeface;

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private TabLayout mTabLayout;

    private static String targetLang;




    private static int[] ENG_correct = new int[5000];
    private static int[] ENG_total = new int[5000];





    public void initializeTarget() {

        System.out.println("********Intitialized CardsFragment with " + targetLang);

        CardsFragment cardsFragment = (CardsFragment) getSupportFragmentManager().findFragmentById(R.id.container_b);
        cardsFragment.initializeSourceDeck(targetLang);




    }


    @Override
    public void createCardDeck(int start, int end, String target, String source) {

        System.out.println("************createCardDeck Called" + ENG_correct[1]);
        targetLang = target;



        CardsFragment cardsFragment = (CardsFragment) getSupportFragmentManager().findFragmentById(R.id.container_b);
        cardsFragment.setCardDeck(start, end, target, source);





    }





    @Override
    public void passCardData(int[] correct, int[] total) {

        System.out.println("***********passCardData called " + correct[1]);

        ENG_correct = correct;
        ENG_total = total;


        CardsFragment cardsFragment = (CardsFragment) getSupportFragmentManager().findFragmentById(R.id.container_b);






    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        System.out.println("**************onCreate called " + ENG_correct[1]);

        typeface = Typeface.createFromAsset(getAssets(),"banksia.ttf");


        mAuth = FirebaseAuth.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.mainPageToolbar);
        mViewPager = (ViewPager) findViewById(R.id.main_tabPager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        setSupportActionBar(mToolbar);


        getSupportActionBar().setTitle("WordChai");

        //Initialize Target Language
        if(targetLang == null){
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                targetLang = extras.getString("TargetLanguage");



            }

            initializeTarget();

        }


        Typeface typefaceENG = Typeface.createFromAsset(getApplication().getAssets(), "chibi.ttf");

        MiscFragment miscFragment = (MiscFragment) getSupportFragmentManager().findFragmentById(R.id.container_a);
        miscFragment.setSourceTargetLanguages(targetLang);




    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        System.out.println("**************onStart called " + ENG_correct[1]);




    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//
//        return true;
//    }












}
