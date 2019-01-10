package com.psiarb.go.wordchai;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardsFragment extends Fragment {

    //**************** VOCABULARY FLASH CARDS

    private Typeface typefaceENG;
    private Typeface typefacePAR;
    private Typeface typefaceARA;
    private Typeface typefaceCHN;
    private Typeface typefaceJPN;
    private Typeface typefaceTHA;
    private Typeface typefaceFRN;
    private Typeface typefaceFIL;
    private Typeface typefaceKOR;
    private Typeface typefacePOL;
    private Typeface typefacePRT;
    private Typeface typefaceSPN;
    private Typeface typefaceVET;


    private static String tempLangDeck[] = new String[25];
    private static String tempSourceLangDeck[] = new String[25];
    private static String targetLangDeck[] = new String[5000];
    private static String sourceLangDeck[] = new String[5000];

    private static String target = "";
    private static String source = "";

    //EnglishTree
    private static String englishVocab[] = new String[5000];
    private static String persian_to_englishString[] = new String[5000];
    private static String persian_to_englishStringB[] = new String[264];   //Temp[] to append extra remaining Strings

    //PersianTree
   // private static String persianVocab[] = new String[5000];
   // private static String persianVocabB[] = new String[1931];
   // private static String english_to_persianString[] = new String[5000];


    //************************************* Substitution (English word frequency only)

    //JapaneseTree
    private static String japaneseVocab[] = new String[5000];
    private static String english_to_japaneseString[] = new String[5000];

    //ChineseTree (Substitute)
    private static String chineseVocab[] = new String[5000];
    private static String english_to_chineseString[] = new String[5000];

    //FrenchTree
    private static String frenchVocab[] = new String[5000];
    private static String french_to_englishString[] = new String[5000];

    //KoreanTree
    private static String koreanVocab[] = new String[5000];
    private static String korean_to_englishString[] = new String[5000];

    //SpanishTree
    private static String spanishVocab[] = new String[5000];
    private static String spanish_to_englishString[] = new String[5000];

    //PortugueseTree
    private static String portugueseVocab[] = new String[5000];
    private static String portuguese_to_englishString[] = new String[5000];

    //PolishTree
    private static String polishVocab[] = new String[5000];
    private static String polish_to_englishString[] = new String[5000];

    //VietnameseTree
    private static String vietnameseVocab[] = new String[5000];
    private static String vietnamese_to_englishString[] = new String[5000];

    //ArabicTree
    private static String arabicVocab[] = new String[5000];
    private static String arabic_to_englishString[] = new String[5000];

    //FilipinoTree
    private static String filipinoVocab[] = new String[5000];
    private static String filipino_to_englishString[] = new String[5000];

    //****************************************

    private static Random rand = new Random();

    CardsFragmentListener cardsFragmentlistener;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static SharedPreferences settings;
    private static final String PREFS_NAME= "myPrefsFile";


    private static int[] ENG_correct = new int[5000];
    private static int[] ENG_total = new int[5000];

    private static TextView targetLang, checkAnswer;
    private Button OK, switchBtn;
    private Button A, B, C, D;
    private static int correctCard;
    private static boolean pressOK;
    private static boolean reverseShuffle;


    private static int mStart, mEnd =0;

    public interface CardsFragmentListener{

        public void passCardData(int[] correct, int[] total);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            cardsFragmentlistener = (CardsFragmentListener) activity;
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public CardsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container_a,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
//        load();
        View view = inflater.inflate(R.layout.fragment_cards,container_a,false);



        OK = (Button) view.findViewById(R.id.OKBtn);
        switchBtn = (Button) view.findViewById(R.id.switchBtn);

        A = (Button) view.findViewById(R.id.A);
        B = (Button) view.findViewById(R.id.B);
        C = (Button) view.findViewById(R.id.C);
        D = (Button) view.findViewById(R.id.D);

        targetLang = (TextView) view.findViewById(R.id.TargetLang);
        checkAnswer = (TextView) view.findViewById(R.id.checkAnswer);

        typefaceENG = Typeface.createFromAsset(getActivity().getAssets(),"EnglishApple.ttf");
        typefacePAR = Typeface.createFromAsset(getActivity().getAssets(),"Sols.ttf");;
        typefaceARA = Typeface.createFromAsset(getActivity().getAssets(),"ae_Sindbad.ttf");;
        typefaceCHN = Typeface.createFromAsset(getActivity().getAssets(),"chinese.ttf");;
        typefaceJPN = Typeface.createFromAsset(getActivity().getAssets(),"uzura.ttf");;
        typefaceTHA = Typeface.createFromAsset(getActivity().getAssets(),"coolThai.ttf");;
        typefaceFRN = Typeface.createFromAsset(getActivity().getAssets(),"Didactic-Regular.otf");;
        typefaceFIL = Typeface.createFromAsset(getActivity().getAssets(),"marvel.ttf");;
        typefaceKOR = Typeface.createFromAsset(getActivity().getAssets(),"krr.ttf");;
        typefacePOL = Typeface.createFromAsset(getActivity().getAssets(),"Blanket.otf");;
        typefacePRT = Typeface.createFromAsset(getActivity().getAssets(),"port.ttf");;
        typefaceSPN = Typeface.createFromAsset(getActivity().getAssets(),"Jura-Regular.otf");;
        typefaceVET = Typeface.createFromAsset(getActivity().getAssets(),"spectral.ttf");;

        checkAnswer.setTypeface(typefaceENG);
        targetLang.setTypeface(typefaceENG);
        A.setTypeface(typefaceENG);
        B.setTypeface(typefaceENG);
        C.setTypeface(typefaceENG);
        D.setTypeface(typefaceENG);

        A.setTransformationMethod(null);
        B.setTransformationMethod(null);
        C.setTransformationMethod(null);
        D.setTransformationMethod(null);


        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAnswer.setText("");

                reverseShuffle = false;
                answerRestart();


                shuffleDeck();





//
//                System.out.println("**************A button called " + ENG_correct[1]);
//
//
//


            }
        });

        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(mEnd != 0){

                answerComplete();
                if(correctCard == 0){


                    correctCardSelected();
                }else{
                    checkAnswer.setText("Wrong");
                };
            }

            }
        });

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mEnd != 0){     answerComplete();
                    if(correctCard == 1){

                        correctCardSelected();
                    }else{
                        checkAnswer.setText("Wrong");
                    };}


            }
        });

        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(mEnd != 0){


                    answerComplete();
                    if(correctCard == 2){

                        correctCardSelected();
                    }else{
                        checkAnswer.setText("Wrong");
                    };
                }



            }
        });

        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mEnd != 0){

                    answerComplete();
                    if(correctCard == 3){

                        correctCardSelected();
                    }else{
                        checkAnswer.setText("Wrong");
                    };

                }



            }
        });



        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAnswer.setText("");
                reverseShuffle = true;

                identifySource();
                identifyTarget();
                answerRestart();
                shuffleDeckReverse();

            }
        });



        //Call all vocabulary parsing methods to store card data
        Tree_Cards();

        persian_to_english_Cards();
        targetLang.setText(persian_to_englishString[4999]);
      //  English_to_persian_Cards();


        shuffleDeck();

        return view;

    }




    //initializeSourceDeck

    public void initializeSourceDeck(String target){

      this.target = target;
        System.out.println("*********************SUCCESS " + this.target);

        }


    public void setCardDeck(int start, int end, String target, String source){

        System.out.println("**************setCardDeck CardsFragment called " + ENG_correct[1]);

        this.mStart = start;
        this.mEnd = end;
        this.target = target;
        this.source = source;


        sourceLangDeck = identifySource();
        targetLangDeck = identifyTarget();




        //Extract Strings from Bank of 5000 and load them into a deck of 25
        for(int i = mStart-1, j = 0; i <= mEnd-1; i++, j++){



            try{
                tempLangDeck[j] = targetLangDeck[i];
                tempSourceLangDeck[j] = sourceLangDeck[i];

            }catch(Exception e){
                System.out.println("FINISH THE THAAANG :)");
            }


        }
        int testCheck =0;
        for(String k : tempLangDeck){
            testCheck++;
            System.out.println(testCheck + ": " + k);
        }

     //   System.out.println("JOEL PARIZI's STRING: " + english_to_persianString[2798]);

        System.out.println("target:" + target + " and source:  " + source + " \t " + mStart + "-" + mEnd) ;

    }


    //************************** SharedPreferences Methods for Data Storage
    public void save(){

        final List<String> words = new ArrayList<String>();

        String index = "";
        //Collects all Strings in array

        for(int i = 0; i < ENG_correct.length; i++){

            index = String.valueOf(ENG_correct[i]);
            words.add(index);
        }

        System.out.println("INPREFS" + ENG_correct[1] + "\n" + words.get(1));

        StringBuilder sb = new StringBuilder();
        for(String str : words){
            sb.append(str);
            sb.append(", ");

        }

        settings = this.getActivity().getSharedPreferences("PREFS",  Context.MODE_PRIVATE);   /// POTENTIAL FAULT DETECTED
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("words", sb.toString());
        editor.commit();

    }

    public void load(){

        System.out.println("in load" + ENG_correct[1]);
        //get data back
        SharedPreferences settings =  this.getActivity().getSharedPreferences("PREFS",  Context.MODE_PRIVATE);
        String wordsString = settings.getString("words","");
        String[] itemsWords = wordsString.split(", ");
        List<String> items = new ArrayList<String>();

        for(int i =0; i < itemsWords.length; i++){
            items.add(itemsWords[i]);
        }

        //Convert the Strings into Integers
        for( int i =0; i < 5000; i++){
//            ENG_correct[i] = Integer.parseInt(items.get(i));
        }

   System.out.println("*********************" + ENG_correct[1]);

    }

    public static void Tree_Cards(){

        //****************Parse 5000 strings from EnglishTree
        EnglishTree eng = new EnglishTree();
        eng.englishParse(englishVocab);

     //   PersianTree per = new PersianTree();
     //   per.persianParse(persianVocab);
     //   PersianTreeB per2 = new PersianTreeB();
     //   per2.persianParseB(persianVocabB);

        //Substitution
        JapaneseTree jpn = new JapaneseTree();
        jpn.japaneseParse(japaneseVocab);

        //Substitution
        ChineseTree chn = new ChineseTree();
        chn.chineseParse(chineseVocab);

        //Substitution
        FrenchTree frn = new FrenchTree();
        frn.frenchParse(frenchVocab);

        //Substitution
        SpanishTree spn = new SpanishTree();
        spn.spanishParse(spanishVocab);

        //Substitution
        PortugueseTree por = new PortugueseTree();
        por.portugueseParse(portugueseVocab);

        //Substitution
        PolishTree pol = new PolishTree();
        pol.polishParse(polishVocab);

        //Substitution
        VietnameseTree vet = new VietnameseTree();
        vet.vietnameseParse(vietnameseVocab);

        //Substitution
        ArabicTree arb = new ArabicTree();
        arb.arabicParse(arabicVocab);

        //Substitution
        FilipinoTree fil = new FilipinoTree();
        fil.filipinoParse(filipinoVocab);

        //Substitution
        //KoreanTree krn = new KoreanTree();
        //krn.koreanParse(koreanVocab);


        for(int i = 3070 ; i <= 4999; i++){

       //     persianVocab[i] = persianVocabB[i-3070];
        }



     //   System.out.println("*****************PERSIAN VOCAB " + persianVocab[4999]);
    }

    public static void English_to_persian_Cards(){

     //   English_To_Persian engpers = new English_To_Persian();
     //   engpers.english_to_persianParser(english_to_persianString);
    }



    public static void persian_to_english_Cards(){

        //*****************************************************PARSE 5000 STRINGS FROM ARRAY LANGUAGE BANK


        Persian_to_English pers = new Persian_to_English();
        pers.persian_to_English_Parser(persian_to_englishString);

        Persian_to_EnglishB pers2 = new Persian_to_EnglishB();
        pers2.persian_to_English_ParserB(persian_to_englishStringB);


        System.out.println("ARRB" + persian_to_englishStringB[262]);
        System.out.println(persian_to_englishString[4999]);


        for(int i = 4737 ; i <= 4999; i++){

            persian_to_englishString[i] = persian_to_englishStringB[i-4737];
        }







//        Arrays.fill(arr, null);
  //      Arrays.fill(arrB, null);


    }


    private String[] identifyTarget(){

        //Load deck with this language's unique high frequency vocabulary list

        switch( source ){

            case "English":{
                A.setTypeface(typefaceENG);
                B.setTypeface(typefaceENG);
                C.setTypeface(typefaceENG);
                D.setTypeface(typefaceENG);



            }
            case "Persian":{
                A.setTypeface(typefacePAR);
                B.setTypeface(typefacePAR);
                C.setTypeface(typefacePAR);
                D.setTypeface(typefacePAR);


            }
            case "Japanese":{
                A.setTypeface(typefaceJPN);
                B.setTypeface(typefaceJPN);
                C.setTypeface(typefaceJPN);
                D.setTypeface(typefaceJPN);



            }
            case "Chinese":{
                A.setTypeface(typefaceCHN);
                B.setTypeface(typefaceCHN);
                C.setTypeface(typefaceCHN);
                D.setTypeface(typefaceCHN);


            }
            case "French":{
                A.setTypeface(typefaceFRN);
                B.setTypeface(typefaceFRN);
                C.setTypeface(typefaceFRN);
                D.setTypeface(typefaceFRN);



            }
            case "Korean":{
                A.setTypeface(typefaceKOR);
                B.setTypeface(typefaceKOR);
                C.setTypeface(typefaceKOR);
                D.setTypeface(typefaceKOR);



            }
            case "Spanish":{
                A.setTypeface(typefaceSPN);
                B.setTypeface(typefaceSPN);
                C.setTypeface(typefaceSPN);
                D.setTypeface(typefaceSPN);


            }
            case "Portuguese":{
                A.setTypeface(typefacePRT);
                B.setTypeface(typefacePRT);
                C.setTypeface(typefacePRT);
                D.setTypeface(typefacePRT);



            }
            case "Polish":{
                A.setTypeface(typefacePOL);
                B.setTypeface(typefacePOL);
                C.setTypeface(typefacePOL);
                D.setTypeface(typefacePOL);



            }
            case "Vietnamese":{
                A.setTypeface(typefaceVET);
                B.setTypeface(typefaceVET);
                C.setTypeface(typefaceVET);
                D.setTypeface(typefaceVET);



            }
            case "Arabic":{
                A.setTypeface(typefaceARA);
                B.setTypeface(typefaceARA);
                C.setTypeface(typefaceARA);
                D.setTypeface(typefaceARA);



            }
            case "Filipino":{
                A.setTypeface(typefaceFIL);
                B.setTypeface(typefaceFIL);
                C.setTypeface(typefaceFIL);
                D.setTypeface(typefaceFIL);


            }
        }


        switch( target ){

            case "English":{
                targetLang.setTypeface(typefaceENG);
                return englishVocab;

            }
            case "Persian":{
                targetLang.setTypeface(typefacePAR);

                return persian_to_englishString;
            }
            case "Japanese":{
                targetLang.setTypeface(typefaceJPN);

                return japaneseVocab;
            }
            case "Chinese":{
                targetLang.setTypeface(typefaceCHN);

                return chineseVocab;
            }
            case "French":{
                targetLang.setTypeface(typefaceFRN);

                return frenchVocab;
            }
            case "Korean":{
                targetLang.setTypeface(typefaceKOR);

                return koreanVocab;
            }
            case "Spanish":{
                targetLang.setTypeface(typefaceSPN);

                return spanishVocab;
            }
            case "Portuguese":{
                targetLang.setTypeface(typefacePRT);

                return portugueseVocab;
            }
            case "Polish":{
                targetLang.setTypeface(typefacePOL);

                return polishVocab;
            }
            case "Vietnamese":{
                targetLang.setTypeface(typefaceVET);

                return vietnameseVocab;
            }
            case "Arabic":{
                targetLang.setTypeface(typefaceARA);

                return arabicVocab;
            }
            case "Filipino":{
                targetLang.setTypeface(typefaceFIL);

                return filipinoVocab;
            }
        }

        return null;
    }

    private String[] identifySource() {



        //If user is learning English
        if(target.equals("English")){

            switch( source ){

                case "Persian":{

                    return persian_to_englishString;
                }


                case "English":{
                    return englishVocab;
                }

                case "Japanese":{
                    return japaneseVocab;
                }
                case "Chinese":{
                    return chineseVocab;
                }
                case "French":{
                    return frenchVocab;
                }
                case "Korean":{
                    return koreanVocab;
                }
                case "Spanish":{
                    return spanishVocab;
                }
                case "Portuguese":{
                    return portugueseVocab;
                }
                case "Polish":{
                    return polishVocab;
                }
                case "Vietnamese":{
                    return vietnameseVocab;
                }
                case "Arabic":{
                    return arabicVocab;
                }
                case "Filipino":{
                    return filipinoVocab;
                }

            }
        }

        //If user is learning Persian
        if(target.equals("Persian")){

            switch( source ){

                case "English":{
                    return englishVocab;

                }

                case "Persian":{
                    return persian_to_englishString;
                }
                case "Japanese":{
                    return japaneseVocab;
                }
                case "Chinese":{
                    return chineseVocab;
                }
                case "French":{
                    return frenchVocab;
                }
                case "Korean":{
                    return koreanVocab;
                }
                case "Spanish":{
                    return spanishVocab;
                }
                case "Portuguese":{
                    return portugueseVocab;
                }
                case "Polish":{
                    return polishVocab;
                }
                case "Vietnamese":{
                    return vietnameseVocab;
                }
                case "Arabic":{
                    return arabicVocab;
                }
                case "Filipino":{
                    return filipinoVocab;
                }

            }
        }

        //If user is learning Japanese
        if(target.equals("Japanese")){

            switch( source ){

                case "English":{
                    return englishVocab;

                }

                case "Persian":{
                    return persian_to_englishString;
                }
                case "Japanese":{
                    return japaneseVocab;
                }
                case "Chinese":{
                    return chineseVocab;
                }
                case "French":{
                    return frenchVocab;
                }
                case "Korean":{
                    return koreanVocab;
                }
                case "Spanish":{
                    return spanishVocab;
                }
                case "Portuguese":{
                    return portugueseVocab;
                }
                case "Polish":{
                    return polishVocab;
                }
                case "Vietnamese":{
                    return vietnameseVocab;
                }
                case "Arabic":{
                    return arabicVocab;
                }
                case "Filipino":{
                    return filipinoVocab;
                }


            }
        }

        //If user is learning Chinese

        if(target.equals("Chinese")){

            switch( source ){

                case "English":{
                    return englishVocab;

                }

                case "Persian":{
                    return persian_to_englishString;
                }
                case "Japanese":{
                    return japaneseVocab;
                }
                case "Chinese":{
                    return chineseVocab;
                }
                case "French":{
                    return frenchVocab;
                }
                case "Korean":{
                    return koreanVocab;
                }
                case "Spanish":{
                    return spanishVocab;
                }
                case "Portuguese":{
                    return portugueseVocab;
                }
                case "Polish":{
                    return polishVocab;
                }
                case "Vietnamese":{
                    return vietnameseVocab;
                }
                case "Arabic":{
                    return arabicVocab;
                }
                case "Filipino":{
                    return filipinoVocab;
                }

            }
        }
        //If user is learning French

        if(target.equals("French")){

            switch( source ){

                case "English":{
                    return englishVocab;

                }

                case "Persian":{
                    return persian_to_englishString;
                }
                case "Japanese":{
                    return japaneseVocab;
                }
                case "Chinese":{
                    return chineseVocab;
                }
                case "French":{
                    return frenchVocab;
                }
                case "Korean":{
                    return koreanVocab;
                }
                case "Portuguese":{
                    return portugueseVocab;
                }
                case "Polish":{
                    return polishVocab;
                }
                case "Vietnamese":{
                    return vietnameseVocab;
                }
                case "Arabic":{
                    return arabicVocab;
                }
                case "Filipino":{
                    return filipinoVocab;
                }
            }
        }
//If user is learning Korean

        if(target.equals("Korean")){

            switch( source ){

                case "English":{
                    return englishVocab;

                }

                case "Persian":{
                    return persian_to_englishString;
                }
                case "Japanese":{
                    return japaneseVocab;
                }
                case "Chinese":{
                    return chineseVocab;
                }
                case "French":{
                    return frenchVocab;
                }
                case "Korean":{
                    return koreanVocab;
                }
                case "Spanish":{
                    return spanishVocab;
                }
                case "Portuguese":{
                    return portugueseVocab;
                }
                case "Polish":{
                    return polishVocab;
                }
                case "Vietnamese":{
                    return vietnameseVocab;
                }
                case "Arabic":{
                    return arabicVocab;
                }
                case "Filipino":{
                    return filipinoVocab;
                }

            }

        }

        //If user is learning Spanish


        if(target.equals("Spanish")){

            switch( source ){

                case "English":{
                    return englishVocab;

                }

                case "Persian":{
                    return persian_to_englishString;
                }
                case "Japanese":{
                    return japaneseVocab;
                }
                case "Chinese":{
                    return chineseVocab;
                }
                case "French":{
                    return frenchVocab;
                }
                case "Korean":{
                    return koreanVocab;
                }
                case "Spanish":{
                    return spanishVocab;
                }
                case "Portguese":{
                    return portugueseVocab;
                }
                case "Polish":{
                    return polishVocab;
                }
                case "Vietnamese":{
                    return vietnameseVocab;
                }
                case "Arabic":{
                    return arabicVocab;
                }
                case "Filipino":{
                    return filipinoVocab;
                }
            }
        }

        //If user is learning Portuguese


        if(target.equals("Portuguese")){

            switch( source ){

                case "English":{
                    return englishVocab;

                }

                case "Persian":{
                    return persian_to_englishString;
                }
                case "Japanese":{
                    return japaneseVocab;
                }
                case "Chinese":{
                    return chineseVocab;
                }
                case "French":{
                    return frenchVocab;
                }
                case "Korean":{
                    return koreanVocab;
                }
                case "Spanish":{
                    return spanishVocab;
                }
                case "Portguese":{
                    return portugueseVocab;
                }
                case "Polish":{
                    return polishVocab;
                }
                case "Vietnamese":{
                    return vietnameseVocab;
                }
                case "Arabic":{
                    return arabicVocab;
                }
                case "Filipino":{
                    return filipinoVocab;
                }
            }
        }

        //If user is learning Polish


        if(target.equals("Polish")){

            switch( source ){

                case "English":{
                    return englishVocab;

                }

                case "Persian":{
                    return persian_to_englishString;
                }
                case "Japanese":{
                    return japaneseVocab;
                }
                case "Chinese":{
                    return chineseVocab;
                }
                case "French":{
                    return frenchVocab;
                }
                case "Korean":{
                    return koreanVocab;
                }
                case "Spanish":{
                    return spanishVocab;
                }
                case "Portguese":{
                    return portugueseVocab;
                }
                case "Polish":{
                    return polishVocab;
                }
                case "Vietnamese":{
                    return vietnameseVocab;
                }
                case "Arabic":{
                    return arabicVocab;
                }
                case "Filipino":{
                    return filipinoVocab;
                }
            }
        }

        //If user is learning Vietnamese

        if(target.equals("Vietnamese")){

            switch( source ){

                case "English":{
                    return englishVocab;

                }

                case "Persian":{
                    return persian_to_englishString;
                }
                case "Japanese":{
                    return japaneseVocab;
                }
                case "Chinese":{
                    return chineseVocab;
                }
                case "French":{
                    return frenchVocab;
                }
                case "Korean":{
                    return koreanVocab;
                }
                case "Spanish":{
                    return spanishVocab;
                }
                case "Portguese":{
                    return portugueseVocab;
                }
                case "Polish":{
                    return polishVocab;
                }
                case "Vietnamese":{
                    return vietnameseVocab;
                }
                case "Arabic":{
                    return arabicVocab;
                }
                case "Filipino":{
                    return filipinoVocab;
                }

            }
        }

        //If user is learning Arabic

        if(target.equals("Arabic")){

            switch( source ){

                case "English":{
                    return englishVocab;

                }

                case "Persian":{
                    return persian_to_englishString;
                }
                case "Japanese":{
                    return japaneseVocab;
                }
                case "Chinese":{
                    return chineseVocab;
                }
                case "French":{
                    return frenchVocab;
                }
                case "Korean":{
                    return koreanVocab;
                }
                case "Spanish":{
                    return spanishVocab;
                }
                case "Portguese":{
                    return portugueseVocab;
                }
                case "Polish":{
                    return polishVocab;
                }
                case "Vietnamese":{
                    return vietnameseVocab;
                }
                case "Arabic":{
                    return arabicVocab;
                }
                case "Filipino":{
                    return filipinoVocab;
                }
            }
        }


        //If user is learning Filipino

        if(target.equals("Filipino")){

            switch( source ){

                case "English":{
                    return englishVocab;

                }

                case "Persian":{
                    return persian_to_englishString;
                }
                case "Japanese":{
                    return japaneseVocab;
                }
                case "Chinese":{
                    return chineseVocab;
                }
                case "French":{
                    return frenchVocab;
                }
                case "Korean":{
                    return koreanVocab;
                }
                case "Spanish":{
                    return spanishVocab;
                }
                case "Portguese":{
                    return portugueseVocab;
                }
                case "Polish":{
                    return polishVocab;
                }
                case "Vietnamese":{
                    return vietnameseVocab;
                }
                case "Arabic":{
                    return arabicVocab;
                }
                case "Filipino":{
                    return filipinoVocab;
                }
            }
        }

        return null;
    }

    public void shuffleDeck(){

        int randA, randB, randC, randD = 0;
        int randCard =0;


        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<25; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0; i<5; i++) {




            System.out.println(list.get(i));

        }
        randA = list.get(0);
        randB = list.get(1);
        randC = list.get(2);
        randD = list.get(3);
        randCard = list.get(4);



        System.out.println(randA + " " + randB + " " + randC + " " + randD);


        correctCard = rand.nextInt(4);

        switch (correctCard) {
            case 0:
                randA = randCard;
                A.setText(tempSourceLangDeck[randA]);
                break;
            case 1:
                randB = randCard;
                B.setText(tempSourceLangDeck[randB]);
                break;
            case 2:
                randC = randCard;
                C.setText(tempSourceLangDeck[randC]);
                break;
            case 3:
                randD = randCard;
                D.setText(tempSourceLangDeck[randD]);
        }

        targetLang.setText(tempLangDeck[randCard]);
        A.setText(tempSourceLangDeck[randA]);
        B.setText(tempSourceLangDeck[randB]);
        C.setText(tempSourceLangDeck[randC]);
        D.setText(tempSourceLangDeck[randD]);

    }

    private void shuffleDeckReverse() {

        int randA, randB, randC, randD = 0;
        int randCard =0;


        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<25; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0; i<5; i++) {




            System.out.println(list.get(i));

        }
        randA = list.get(0);
        randB = list.get(1);
        randC = list.get(2);
        randD = list.get(3);
        randCard = list.get(4);



        System.out.println(randA + " " + randB + " " + randC + " " + randD);


        correctCard = rand.nextInt(4);

        switch (correctCard) {
            case 0:
                randA = randCard;
                A.setText(tempLangDeck[randA]);
                break;
            case 1:
                randB = randCard;
                B.setText(tempLangDeck[randB]);
                break;
            case 2:
                randC = randCard;
                C.setText(tempLangDeck[randC]);
                break;
            case 3:
                randD = randCard;
                D.setText(tempLangDeck[randD]);
        }

        targetLang.setText(tempSourceLangDeck[randCard]);
        A.setText(tempLangDeck[randA]);
        B.setText(tempLangDeck[randB]);
        C.setText(tempLangDeck[randC]);
        D.setText(tempLangDeck[randD]);
    }



    private void answerComplete(){
        A.setEnabled(false);
        B.setEnabled(false);
        C.setEnabled(false);
        D.setEnabled(false);
    }
    private void answerRestart(){
        A.setEnabled(true);
        B.setEnabled(true);
        C.setEnabled(true);
        D.setEnabled(true);
    }

    public void correctCardSelected(){


        checkAnswer.setText("CORRECT");


        answerComplete();

        pressOK = true;

//        if(target == "Persian"){
//
//            Arrays.asList(tempLangDeck).contains("gets");
//
//            ENG_correct[1]++;
//            ENG_total[]++;



     //   System.out.println(ENG_correct[1]);

        save();
        load();

    }

}