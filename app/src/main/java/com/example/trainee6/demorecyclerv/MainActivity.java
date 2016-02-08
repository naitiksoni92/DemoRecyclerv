package com.example.trainee6.demorecyclerv;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trainee6.demorecyclerv.app.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private List<DataBeanDriver> driverList = new ArrayList<>();
    private List<DataBeanDriver> untouchList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DriverAdapter dAdapter;
    private SearchView searchView;
    //private EditText txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "This is snackbar message !!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                try{
                    Cache cache = AppController.getInstance().getRequestQueue().getCache();
                    Cache.Entry entry = cache.get("http://api.androidhive.info/volley/person_object.json");
                    if(entry != null){
                        try {
                            String data = new String(entry.data, "UTF-8");
                            Toast.makeText(getApplicationContext(), "Fromcache="+data.toString(),Toast.LENGTH_SHORT).show();

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                else{
                    // Cached response doesn't exists. Make network call here
                        getJsonObjResponse();
                        //getJsonArrResponse();
                }
                }
                catch (Exception w)
                {
                    Log.e("VOLLEYERROR",w.getMessage());
                }
            }
        });*/

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //DataBeanDriver driver = driverList.get(position);
                //Toast.makeText(getApplicationContext(), driver.getId() + " is ID!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                //DataBeanDriver driver = driverList.get(position);
                //Toast.makeText(getApplicationContext(), driver.getName() + " is Name of driver!", Toast.LENGTH_SHORT).show();
                //driverList.remove(position);
                //recyclerView.getAdapter().notifyDataSetChanged();
                dAdapter.removeItem(position);
            }
        }));

    }


    @Override
    protected void onResume() {
        super.onResume();

        dAdapter = new DriverAdapter(driverList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(dAdapter);
        prepareDriverData();

    }

    //Volley get jsonobject method
    public void getJsonObjResponse()
    {
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "http://api.androidhive.info/volley/person_object.json";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), response.toString(),Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Some error",Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    public void getJsonArrResponse()
    {
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";

        String url = "http://api.androidhive.info/volley/person_array.json";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("VOLLEY", response.toString());
                        Toast.makeText(getApplicationContext(),response.toString() ,Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Some error",Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    /*public void getJsonImgResponse()
    {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        // If you are using normal ImageView
        imageLoader.get("", new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                            imageView.setImageBitmap(response.getBitmap());
                }
            }
        });
    }*/

    private void prepareDriverData() {

        driverList.add(new DataBeanDriver("Maruti Suzuki","WagonR","GJ 8 AF 7200","naitiksoni92@gmail.com",12,"A120045886922","09429289190","Naitik Soni",0));
        driverList.add(new DataBeanDriver("Fiat","Punto","GJ 1 AF 8700","parisoni12@gmail.com",5,"P580065001252","09429481226","Parikshit Soni",0));
        driverList.add(new DataBeanDriver("AUDI","A4","GJ 2 AF 2700","parisoni12@gmail.com",10,"P580065001252","09585463258","Vaibhav Dholkiya",1));
        driverList.add(new DataBeanDriver("BMW","D206","GJ 8 AF 5500","parisoni12@gmail.com",3,"P580065001252","09962587410","Parth Soni",2));
        driverList.add(new DataBeanDriver("Fiat","Punto","GJ 1 AF 8700","parisoni12@gmail.com",15,"P580065001252","09429481226","Prakash Soni",1));
        untouchList=driverList;
        dAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                List<DataBeanDriver> filteredModelList= filter(untouchList, query);
                dAdapter.animateTo(filteredModelList);
                dAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);
                Log.d("SEARCH","---onQueryTextChange called---"+query);
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose(){
                driverList.clear();
                prepareDriverData();
                dAdapter = new DriverAdapter(driverList);
                recyclerView.setAdapter(dAdapter);
                untouchList=driverList;
                dAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }


    private List<DataBeanDriver> filter(List<DataBeanDriver> models, String query) {
        query = query.toLowerCase();
        final List<DataBeanDriver> filteredModelList = new ArrayList<>();
        filteredModelList.clear();

        for (DataBeanDriver model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        Log.d("SEARCH","---Filter called---"+query);
        return filteredModelList;
    }

    /*@Override
    public void onBackPressed() {

        if (!searchView.isIconified()) {
            searchView.setIconified(true);

            //prepareDriverData();
            //dAdapter = new DriverAdapter(driverList);
            //recyclerView.setAdapter(dAdapter);
            //dAdapter.notifyDataSetChanged();
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_map) {
            //Toast.makeText(this,"SETTING !!",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_user) {
            //AppController.getInstance().getRequestQueue().getCache().remove("http://api.androidhive.info/volley/person_object.json");
            //Toast.makeText(this, "Cleared !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_filter) {
            //Toast.makeText(this, "Cleared !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_noti) {
            //Toast.makeText(this, "Cleared !!", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_search) {
            //Toast.makeText(this, "Cleared !!", Toast.LENGTH_SHORT).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
