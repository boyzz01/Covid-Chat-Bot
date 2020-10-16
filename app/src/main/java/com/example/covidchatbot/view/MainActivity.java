package com.example.covidchatbot.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.covidchatbot.R;
import com.example.covidchatbot.adapter.ChatAdapter;
import com.example.covidchatbot.database.RealmHelper;
import com.example.covidchatbot.model.ChatModel;
import com.example.covidchatbot.model.CountryModel;
import com.example.covidchatbot.response.CountryCaseResponse;
import com.example.covidchatbot.response.CountryListResponse;
import com.example.covidchatbot.response.WorldCaseResponse;
import com.example.covidchatbot.service.BaseApiService;
import com.example.covidchatbot.service.UtilsApi;


import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


     private List<ChatModel> chatList;
     private List<CountryModel> countryList;

     List<CountryListResponse> countryListResponses;
     List<CountryCaseResponse> countryCaseResponses;

     private ChatModel chatModel;
     private WorldCaseResponse worldCaseResponse;

    //constraint layout as button
    private ConstraintLayout listButton,sendButton;


    private EditText chatMessageText;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ChatAdapter chatAdapter;
    private TextView botStatus;
    private ImageView onlineStatus;
    private HorizontalScrollView horizontalScrollView;
    private Button about,preventive,symptoms,myth,query;

    Boolean listVisible= false;

    private Realm realm;
    private RealmHelper realmHelper;

    SimpleDateFormat dateFormat;
    private String time;

    //API
    BaseApiService apiService;

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loadMessage();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = chatMessageText.getText().toString();
              
                if (!message.equals(""))
                {
                    sendMessage(message);
                }
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listVisible)
                {
                    horizontalScrollView.setVisibility(View.GONE);
                    listVisible = false;
                }
                else {
                    horizontalScrollView.setVisibility(View.VISIBLE);
                    listVisible = true;
                }

            }
        });

        initListButton();


    }

    private void initListButton() {
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserChat(about.getText().toString());
                addBotChat("COVID-19 is the infectious disease caused by the most recently discovered coronavirus. This new virus and disease were unknown before the outbreak began in Wuhan, China, in December 2019.\n" +
                        "COVID-19 is now a pandemic affecting many countries globally");
                chatAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(chatList.size() - 1);
            }
        });

        preventive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserChat(preventive.getText().toString());
                addBotChat("You can reduce your chances of being infected or spreading COVID-19 by taking some simple precautions:\n" +
                        "\n" +
                        "    regularly clean your hands with an alcohol-based hand rub or wash them with soap and water;\n" +
                        "    maintain at least 1 meter (3 feet) distance between yourself and anyone who is coughing or sneezing;\n" +
                        "    avoid touching eyes, nose and mouth;\n" +
                        "    make sure you, and the people around you, follow good respiratory hygiene;\n" +
                        "    stay home if you feel unwell. \n" +
                        "\n" +
                        "If you have a fever, cough and difficulty breathing, seek medical attention and call in advance. Follow the directions of your local health authority.");
                chatAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(chatList.size() - 1);
            }
        });

        symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserChat(symptoms.getText().toString());
                addBotChat("The most common symptoms of COVID-19 are fever, dry cough, and tiredness. Other symptoms that are less common and may affect some patients include aches and pains, " +
                        "nasal congestion, headache, conjunctivitis, sore throat, diarrhea, loss of taste or smell or a rash on skin or discoloration of fingers or toes. These symptoms are usually mild and begin gradually." +
                        "Some people become infected but only have very mild symptoms");
                chatAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(chatList.size() - 1);
            }
        });
        myth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserChat(myth.getText().toString());
                addBotChat("In times of COVID-19 spread, the increasing uncertainty and concern of people are the ideal breeding ground for conspiracy theories, false reports or myths. " +
                        "People often find out about the virus on various YouTube channels where online activists believe they know the true origin of the virus. False reports are also increasingly circulating via social media such as Facebook or WhatsApp. " +
                        "This can, for example, lead to increasing panic and panic buying. Therefore, please make sure that you only inform yourself on official websites such as those of the WHO or the ECDC.");
                chatAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(chatList.size() - 1);
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserChat(query.getText().toString());
                addBotChat("You can type\n" +
                        "1. \"Cases Total\" to see active cases of world combined.\n" +
                        "2. \"Deaths Total\" to see total number of deaths of world combined.\n" +
                        "3. \"Cases <country-code>\" to see total active cases in that country, eg. \"Cases in ID\" or \"Cases ID\" or \"Cases in Indonesia\".\n" +
                        "4. \"Deaths <country-code>\" to see total deaths in that country, eg. \"Deaths in ID\" or \"Deaths ID\" or \"Deaths in Indonesia\".\n" +
                        "(Not Case Sensitive)");
                chatAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(chatList.size() - 1);
            }
        });
    }

    private void sendMessage(String message) {

        //add user chat to database
        addUserChat(message);

        chatMessageText.setText("");
        chatAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(chatList.size() - 1);


        if (message.toLowerCase().equals("deaths total") ||message.toLowerCase().equals("total deaths") ||  message.toLowerCase().equals("cases total") || message.toLowerCase().equals("total cases"))
        {
            apiService.getWorldCase().enqueue(new Callback<WorldCaseResponse>() {
                @Override
                public void onResponse(@NotNull Call<WorldCaseResponse> call, @NotNull Response<WorldCaseResponse> response) {
                    //add bot chat to database and show to user
                    worldCaseResponse = response.body();

                    assert worldCaseResponse != null;
                    if (message.toLowerCase().equals("deaths total") || message.toLowerCase().equals("total deaths"))
                    {
                        addBotChat("Total Deaths "+ NumberFormat.getNumberInstance(Locale.US).format(worldCaseResponse.getTotalDeaths()));


                    } else
                    {
                        Long activeCases = worldCaseResponse.getTotalConfirmed() - worldCaseResponse.getTotalDeaths() - worldCaseResponse.getTotalRecovered();
                        addBotChat("Total Active Cases "+NumberFormat.getNumberInstance(Locale.US).format(activeCases));

                    }

                    chatAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(chatList.size() - 1);
                }

                @Override
                public void onFailure(@NotNull Call<WorldCaseResponse> call, @NotNull Throwable t) {
                    noResponse();
                }
            });
        }
        else
        {
            boolean found = false;
            countryList = realmHelper.getAllCountry();
            for (int i=0;i<countryList.size();i++)
            {
                String slug = countryList.get(i).getSlug().toLowerCase();
                String countryName = countryList.get(i).getCountry().toLowerCase();
                String countryCode = countryList.get(i).getiSO2().toLowerCase();
                if (message.toLowerCase().equals("cases "+slug) || message.toLowerCase().equals("cases "+countryName) || message.toLowerCase().equals("cases "+countryCode) ||
                        message.toLowerCase().equals("cases in "+slug) || message.toLowerCase().equals("cases in "+countryName) || message.toLowerCase().equals("cases in "+countryCode) ||
                        message.equals(slug) || message.equals(countryName) || message.equals(countryCode))
                {
                    getCountryCase("cases",countryList.get(i).getSlug());
                    found= true;
                    break;
                }
                else  if (message.toLowerCase().equals("deaths "+slug) || message.toLowerCase().equals("deaths "+countryName) || message.toLowerCase().equals("deaths "+countryCode) ||
                        message.toLowerCase().equals("deaths in "+slug) || message.toLowerCase().equals("deaths in "+countryName) || message.toLowerCase().equals("deaths in "+countryCode))
                {
                    getCountryCase("deaths",countryList.get(i).getSlug());
                    found = true;
                    break;
                }


            }
            if (!found)
            {
                addBotChat("Im sorry, I cant find anything");
                chatAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(chatList.size() - 1);
            }

        }



    }

    private void getCountryCase(String type,String slug) {

        apiService.getCountryCase(slug).enqueue(new Callback<List<CountryCaseResponse>>() {
            @Override
            public void onResponse(Call<List<CountryCaseResponse>> call, Response<List<CountryCaseResponse>> response) {
                countryCaseResponses = response.body();
                ChatModel chatModel = new ChatModel();
                int lastIndex = countryCaseResponses.size()-1;
                String country = slug.substring(0,1).toUpperCase() + slug.substring(1);
                country = country.replaceAll("-"," ");
                if (type.equals("cases")) {
                    addBotChat(country+" Active Cases is "+ NumberFormat.getNumberInstance(Locale.US).format(countryCaseResponses.get(lastIndex).getActive()));
                }
                else {
                    addBotChat(country+" Deaths Cases is "+ NumberFormat.getNumberInstance(Locale.US).format(countryCaseResponses.get(lastIndex).getDeaths()));

                }

                chatAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(chatList.size() - 1);
            }

            @Override
            public void onFailure(Call<List<CountryCaseResponse>> call, Throwable t) {
                noResponse();
            }
        });
    }



    private void loadMessage() {

        chatList = realmHelper.getAllChat();
        chatAdapter = new ChatAdapter(this,chatList);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.scrollToPosition(chatList.size() - 1);


    }

    public boolean hasNetwork()
    {
        Boolean isConnected;

        ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork !=null && activeNetwork.isConnected())
        {

            isConnected = true;
        }
        else
        {
            isConnected = false;
        }
        return isConnected;
    }



    private void getCountry() {

        apiService.getCountryList().enqueue(new Callback<List<CountryListResponse>>() {
            @Override
            public void onResponse(Call<List<CountryListResponse>> call, Response<List<CountryListResponse>> response) {
                countryListResponses = response.body();

                for (int i=0;i<countryListResponses.size();i++)
                {
                    CountryModel countryModel = new CountryModel();
                    countryModel.setCountry(countryListResponses.get(i).getCountry().toLowerCase());
                    countryModel.setSlug(countryListResponses.get(i).getSlug().toLowerCase());
                    countryModel.setiSO2(countryListResponses.get(i).getISO2());
                    realmHelper.addCountry(countryModel);
                }

            }

            @Override
            public void onFailure(Call<List<CountryListResponse>> call, Throwable t) {
               noResponse();
            }
        });
//
    }

    private void init() {
        recyclerView = findViewById(R.id.chatMessageList);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((getApplicationContext()));
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);


        listButton = findViewById(R.id.listButton);
        sendButton = findViewById(R.id.sendButton);
        chatMessageText = findViewById(R.id.chat_message_text);
        botStatus = findViewById(R.id.botStatus);
        onlineStatus = findViewById(R.id.onlineStatus);

        about = findViewById(R.id.btn_about);
        preventive = findViewById(R.id.btn_preventive);
        symptoms = findViewById(R.id.btn_symptoms);
        myth = findViewById(R.id.btn_myth);
        query = findViewById(R.id.btn_query);

        horizontalScrollView = findViewById(R.id.horizontalScrollView);


        apiService = UtilsApi.getAPIService(this);

        //Set up Realm Database
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        dateFormat = new SimpleDateFormat("HH:mm");

        if (hasNetwork())
        {
            botStatus.setText("Online");
            onlineStatus.setImageResource(R.drawable.online);
        }
        else {
            botStatus.setText("Offline");
            onlineStatus.setImageResource(R.drawable.offline);
        }


        if (realmHelper.getAllCountry().isEmpty())
        {
            getCountry();
        }


        //check first time launch
        prefs = getSharedPreferences("data", MODE_PRIVATE);

        if (prefs.getBoolean("firstrun", true)) {
            firstTimeLaunch();
            prefs.edit().putBoolean("firstrun", false).apply();
        }


    }

    private void firstTimeLaunch() {

        addBotChat("Hello, my name is Eruna and I'm here to give information and guidance regarding the current outbreak of COVID-19, such as how to prevent the spreading, or clarify some myths that maybe you have heard.\n" +
                "\n" +
                "How may I help you?");


        horizontalScrollView.setVisibility(View.VISIBLE);
        listVisible = true;
    }

    private void noResponse()
    {

        addBotChat("Im Sorry, I cant connect to the server, please try again later");
        chatAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(chatList.size() - 1);
    }

    private void addBotChat(String message)
    {
        time = dateFormat.format(new Date());
        ChatModel chatModel = new ChatModel();
        chatModel.setMessage(message);
        chatModel.setSender("bot");
        chatModel.setTime(time);
        realmHelper.addChat(chatModel);
    }

    private void addUserChat(String message)
    {
        time = dateFormat.format(new Date());
        ChatModel chatModel = new ChatModel();
        chatModel.setMessage(message);
        chatModel.setSender("user");
        chatModel.setTime(time);
        realmHelper.addChat(chatModel);
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press Back Again To Exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }




}