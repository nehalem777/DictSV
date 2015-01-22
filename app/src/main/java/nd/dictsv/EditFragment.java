package nd.dictsv;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ND on 9/7/2557.
 */
public class EditFragment extends Fragment {
    DBHelper mHelper;
    SQLiteDatabase mDb;
    Cursor mCorsor, mmCursor;
    private EditText edt_addword, edt_addtermino, edt_words, edt_cate, edt_cate_cate;
    private Spinner spn_cate_vocab, spn_categories;
    private Button btn_save_vo, btn_clear_vo, btn_save_cat, btn_clear_cat;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_edit, container, false);
        mHelper = new DBHelper(getActivity());
        mDb = mHelper.getWritableDatabase();
        //initials widget
        initWidgetVocab();
        initWidgetCate();
        LoadCateSpinner();

        //Vocab Button
        btn_save_vo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addWordToTable();
                //Toast.makeText(getActivity(), "คุณกดปุ่มบันทึกแล้ว", Toast.LENGTH_SHORT).show();
            }
        });
        btn_clear_vo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearVocab();
                Toast.makeText(getActivity(), "เคลียร์ข้อมูลแล้ว", Toast.LENGTH_SHORT).show();
            }
        });// End vocab
        //Cate Button
        btn_save_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCateToTable();
            }
        });
        btn_clear_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearCate();
                Toast.makeText(getActivity(), "เคลียร์ข้อมูลแล้ว", Toast.LENGTH_SHORT).show();
            }
        });//End Cate
        return rootView;
    }

    ///
    private void addWordToTable() {
        final String Words = edt_addword.getText().toString();
        final String trans = edt_addtermino.getText().toString();
        final String ter = edt_words.getText().toString();

        long cateid = spn_cate_vocab.getSelectedItemId()+1;
        //final String selected = (String) spn_cate_vocab.getSelectedItem();
        mHelper = new DBHelper(getActivity());
        mDb = mHelper.getReadableDatabase();
        mCorsor = mDb.rawQuery("SELECT *  FROM " + DBHelper.TABLE_CATE, null);
        mmCursor = mDb.rawQuery("SELECT *  FROM " + DBHelper.TABLE_WordTB, null);
        //String anotherway = spn_cate_vocab.getItemAtPosition(position).toString();

        //selected.equals(DBHelper.COL0_CATE_ID)
        if (Words.length() != 0 && trans.length() != 0 && ter.length() != 0) {
            //SELECT column name FROM table name WHERE condition AND auther condition
                        /*Cursor mCursor = mDb.rawQuery("SELECT * FROM WordTB"
                                + " WHERE " + DBHelper.COL1_WORDS + "='" + Words + "'"
                                + " AND " + DBHelper.COL2_TRANSLATER + "='" + trans + "'"
                                + " AND " + DBHelper.COL3_TERMINOLOGY + "='" + ter + "'"
                                + " AND " + DBHelper.COL5_WORD_CATE_ID + "='" + selected + "'"
                                , null);
                        Log.d("TEE", "selectCate : " +selected);

                        if( mCursor.getCount()==0) {*/

            mDb.execSQL("INSERT INTO WordTB"
                    + " (" + DBHelper.COL1_WORDS
                    + ", " + DBHelper.COL2_TRANSLATER
                    + ", " + DBHelper.COL3_TERMINOLOGY
                    + ", " + DBHelper.COL5_WORD_CATE_ID
                    + ") VALUES ('"
                    + Words + "', '" + trans + "', '" + ter + "', '" + cateid +  "'  );");


            clearVocab();
            LoadCateSpinner();
            Toast.makeText(getActivity()
                    , "มีคำอยู่ในระบบแล้ว", Toast.LENGTH_SHORT).show();

                        /*} else {
                            Toast.makeText(getActivity()
                                    , "มีคำอยู่ในระบบแล้ว", Toast.LENGTH_SHORT).show();
                        }
*/
        } else {
            Toast.makeText(getActivity()
                    , "กรุณากรอกข้อมูลให้ครบทุกช่อง", Toast.LENGTH_SHORT).show();
        }


    }

    private void addCateToTable() {
        String cate = edt_cate_cate.getText().toString();

        if (cate.length() != 0) {
            //SELECT column name FROM table name WHERE condition AND auther condition

            Cursor mCursor = mDb.rawQuery("SELECT * FROM " + DBHelper.TABLE_CATE
                    + " WHERE " + DBHelper.COL1_CATENAME + "='" + cate + "'"
                    , null);

            if (mCursor.getCount() == 0) {

                mDb.execSQL("INSERT INTO Cate "
                        + " (" + DBHelper.COL1_CATENAME
                        + ") VALUES ('"
                        + cate + "');");

                clearCate();
                LoadCateSpinner();

                Toast.makeText(getActivity()
                        , "เพิ่มหมวดเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity()
                        , "มีหมวดอยู่ในระบบแล้ว", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getActivity()
                    , "กรุณากรอกหมวด", Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdateWordToTable() {
        String W1_Words = edt_addword.getText().toString();
        String W2_trans = edt_addtermino.getText().toString();
        String W3_ter = edt_words.getText().toString();
        String W4_cate = edt_cate.getText().toString();
    }

    public Cursor LoadCateSpinner() {

        final List<String> arrayList = new ArrayList<String>();
        mHelper = new DBHelper(getActivity());
        mDb = mHelper.getReadableDatabase();
        mCorsor = mDb.rawQuery("SELECT *  FROM " + DBHelper.TABLE_CATE, null);


        if (mCorsor.moveToFirst()) {

            do {
                //arrayList.add(mCorsor.getString(0));
                arrayList.add(mCorsor.getString(1));


            } while (mCorsor.moveToNext());

        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        spn_cate_vocab.setAdapter(arrayAdapter);
        spn_categories.setAdapter(arrayAdapter);


        spn_cate_vocab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity()
                        //โชว์ Cate ID
                        , "Cate Id คือ : "+spn_cate_vocab.getSelectedItemId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return mCorsor;


    }

    /*public void onStop() {
        super.onStop();
        mDb.close();

    }*/
    /// Vocaborations Section in Layout frame
    private void initWidgetVocab() {
        edt_addword = (EditText) rootView.findViewById(R.id.edt_addword);
        edt_addtermino = (EditText) rootView.findViewById(R.id.edt_addtermino);
        edt_words = (EditText) rootView.findViewById(R.id.edt_words);

        spn_cate_vocab = (Spinner) rootView.findViewById(R.id.spn_cate_vocab);
        btn_save_vo = (Button) rootView.findViewById(R.id.btn_save_vo);
        btn_clear_vo = (Button) rootView.findViewById(R.id.btn_clear_vo);
    }//initWidgetVocab

    private void clearVocab() {
        //clear text in word vocab
        edt_addword.setText("");
        edt_addtermino.setText("");
        edt_words.setText("");
    }//clear text

    private void initWidgetCate() {
        edt_cate_cate = (EditText) rootView.findViewById(R.id.edt_cate_cate);
        spn_categories = (Spinner) rootView.findViewById(R.id.spn_categories);
        btn_save_cat = (Button) rootView.findViewById(R.id.btn_save_cat);
        btn_clear_cat = (Button) rootView.findViewById(R.id.btn_clear_cat);
    }

    private void clearCate() {
        edt_cate_cate.setText("");
    }
}
