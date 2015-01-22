package nd.dictsv;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nd.dictsv.Adaptor.CustomLVAdaptor;


/**
 * Created by ND on 9/7/2557.
 */
public class SearchFragment extends Fragment {

    DBHelper mHelper;
    SQLiteDatabase mDb;
    Cursor mCorsorword;
    Cursor mCorsorcate;
    //Declaring
    private EditText edt_search;
    private Button btn_cat;
    private ListView listViewSearch;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        mHelper = new DBHelper(getActivity());
        mDb = mHelper.getReadableDatabase();
        mCorsorword = mDb.rawQuery("SELECT *  FROM " + DBHelper.TABLE_WordTB, null);
        mCorsorcate = mDb.rawQuery("SELECT *  FROM " + DBHelper.TABLE_CATE, null);


        final ArrayList<String> arr_list_cate = new ArrayList<>();
        final ArrayList<String> arr_list_word = new ArrayList<>();
        final ArrayList<String> arr_list_termer = new ArrayList<>();

        mCorsorcate.moveToFirst();
        mCorsorword.moveToFirst();
        while (!mCorsorword.isAfterLast() ){
            arr_list_cate.add(" CateId >>" + mCorsorword.getString(mCorsorword.getColumnIndex(DBHelper.COL5_WORD_CATE_ID)) );
            arr_list_word.add(" Words >>" + mCorsorword.getString(mCorsorword.getColumnIndex(DBHelper.COL1_WORDS)) );
            arr_list_termer.add(" Termer >>" + mCorsorword.getString(mCorsorword.getColumnIndex(DBHelper.COL2_TRANSLATER)) );
            mCorsorword.moveToNext();
        }

        CustomLVAdaptor adaptor = new CustomLVAdaptor(getActivity(),arr_list_cate,arr_list_word,arr_list_termer);

        listViewSearch  = (ListView)rootView.findViewById(R.id.list_item);
        listViewSearch.setAdapter(adaptor);

        //initials widget Words  trans ter
        //initWidgetSearch();

        //setupCustomListView();


        edt_search      = (EditText)rootView.findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Toast.makeText(getActivity(),"baforeTextChange",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // Toast.makeText(getActivity(),"onTextChange",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Toast.makeText(getActivity(),"afTerTextchanged",Toast.LENGTH_LONG).show();
                ArrayList<String> src_list = new ArrayList<String>();
                ArrayList<String> src_list2 = new ArrayList<String>();
                ArrayList<String> src_list3 = new ArrayList<String>();
                int textlength = edt_search.getText().length();
                if (textlength <= 0) {
                    listViewSearch.setAdapter(null);
                } else {
                    for (int i = 0; i < arr_list_cate.size(); i++) {
                        try {
                            if (edt_search.getText().toString().equalsIgnoreCase(arr_list_cate.get(i).subSequence(0, textlength).toString())) {
                                while (!mCorsorword.isAfterLast() ){
                                    src_list.add(" CateId >>" + mCorsorword.getString(mCorsorword.getColumnIndex(DBHelper.COL5_WORD_CATE_ID)) );
                                    src_list2.add(" Words >>" + mCorsorword.getString(mCorsorword.getColumnIndex(DBHelper.COL1_WORDS)) );
                                    src_list3.add(" Termer >>" + mCorsorword.getString(mCorsorword.getColumnIndex(DBHelper.COL2_TRANSLATER)) );
                                    mCorsorword.moveToNext();
                                }

                            }

                        } catch (Exception e) {
                        }


                        listViewSearch.setAdapter(new CustomLVAdaptor(getActivity(), src_list,src_list2,src_list3));
                    }
                }
            }
        });

        return rootView;
    }

    private void setupCustomListView() {

        //setListAdapter(new CustomAdaptor(getActivity().getApplicationContext()));

    }

    //Adaptor For Custom Listview
    public class CustomAdaptor extends BaseAdapter {

        public Context mContext;
        public LayoutInflater mInflater;

        public CustomAdaptor(Context ctx) {
            mContext = ctx;
            mInflater = LayoutInflater.from(mContext);
        }



        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            final ViewHolder viewHolder;


            if (convertView == null) {
                //create convertview & bindWidget
                convertView = mInflater.inflate(R.layout.item_content, null);
                viewHolder = new ViewHolder();
                viewHolder.cate = (TextView) convertView.findViewById(R.id.titleCate);
                viewHolder.word = (TextView) convertView.findViewById(R.id.tv_word);
                viewHolder.termer = (TextView) convertView.findViewById(R.id.tv_termo);

                viewHolder.favImage = (ImageButton) convertView.findViewById(R.id.img_fav);
                viewHolder.soundImage = (ImageButton) convertView.findViewById(R.id.img_sound);
                convertView.setTag(viewHolder);


            } else {
                //rebind widget in convertView
                viewHolder = (ViewHolder) convertView.getTag();

            }
            //update content in convertView
            viewHolder.word.setText(String.valueOf(position + 1));
            viewHolder.word.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "คุณเลือก"+(viewHolder.word).getText(), Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.soundImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ไปยัง method อ่านออกเสียง
                    viewHolder.soundImage.setImageResource(R.drawable.ic_volume_up_grey600_48dp);
                    Toast.makeText(getActivity(), "ฟังเสียงเถอะ", Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.favImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    view.setSelected(!view.isSelected());
                    if (view.isSelected()) {

                        viewHolder.favImage.setImageResource(R.drawable.ic_star_20dp);

                        Toast.makeText(getActivity(), "หมาเย็ดเป็ด เพิ่มรายการโปรดแล้ว" + view.isSelected(), Toast.LENGTH_SHORT).show();

                    } else {

                        viewHolder.favImage.setImageResource(R.drawable.ic_star_outline_20dp);

                        Toast.makeText(getActivity(), "ลบออกจากรายการโปรดแล้ว" + view.isSelected(), Toast.LENGTH_SHORT).show();
                    }
                }


            });
            return convertView;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }


    }

    final class ViewHolder {
        public TextView cate;
        public TextView word;
        public TextView termer;
        public ImageButton favImage;
        public ImageButton soundImage;

    }
    /*private void initWidgetSearch(){
        //in Activity (Button)findViewById(R.id.btn_cat); ไม่มี method getView() หรือ rootview เท่ากับ getview()
        btn_cat         = (Button)rootView.findViewById(R.id.btn_cat);
        edt_search      = (EditText)rootView.findViewById(R.id.edt_search);
        listViewSearch  = (ListView)rootView.findViewById(R.id.list);
    }//initials widget*/
}
