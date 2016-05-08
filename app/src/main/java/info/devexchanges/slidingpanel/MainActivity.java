package info.devexchanges.slidingpanel;

import android.graphics.Color;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SlidingPaneLayout.PanelSlideListener {

    private ListView leftListView;
    private ListView mainListView;
    private String[] asiaCountries;
    private String[] europeCountries;
    private String[] africaCountries;
    private String[] continents;
    private static final String[] NO_DATA = {};
    private ArrayAdapter<String> mainPanelAdapter;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leftListView = (ListView) findViewById(R.id.left_list);
        mainListView = (ListView) findViewById(R.id.main_list);

        SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
        assert slidingPaneLayout != null;
        slidingPaneLayout.setPanelSlideListener(this);
        slidingPaneLayout.setSliderFadeColor(Color.TRANSPARENT);

        //get string arrays resource
        asiaCountries = getResources().getStringArray(R.array.asia);
        europeCountries = getResources().getStringArray(R.array.europe);
        africaCountries = getResources().getStringArray(R.array.africa);
        continents = getResources().getStringArray(R.array.continent);

        //set listviews adapter
        ArrayAdapter<String> leftPaneAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, continents);
        leftListView.setAdapter(leftPaneAdapter);
        mainPanelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, asiaCountries);
        mainListView.setAdapter(mainPanelAdapter);

        //handling left panel listview item click event
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setMainAdapter(asiaCountries);
                } else if (position == 1) {
                    setMainAdapter(europeCountries);
                } else if (position == 2) {
                    setMainAdapter(africaCountries);
                } else {
                    setMainAdapter(NO_DATA);
                    Toast.makeText(MainActivity.this, "No data!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //handling main panel listview item click event
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "You selectd: " + item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setMainAdapter(String[] strings) {
        mainPanelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings);
        mainListView.setAdapter(mainPanelAdapter);
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        Log.i(TAG, "onPanelSlide: " + slideOffset);
    }

    @Override
    public void onPanelOpened(View panel) {
        Log.i(TAG, "onPanelOpened");
        leftListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPanelClosed(View panel) {
        Log.i(TAG, "onPanelClosed");
        leftListView.setVisibility(View.GONE);
    }
}
