package com.vanessapr.appbibliotecafain.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vanessapr.appbibliotecafain.MainActivity;
import com.vanessapr.appbibliotecafain.R;
import com.vanessapr.appbibliotecafain.models.Libro;
import com.vanessapr.appbibliotecafain.models.LibroAdapter;
import com.vanessapr.appbibliotecafain.models.LibroModel;

import java.util.ArrayList;

/**
 * Created by Milagros on 27/10/2014.
 */
public class BooksListFragment extends Fragment {
    private static final String TAG = "BooksListFragment";
    private String where, orderBy;
    private ListView lvBooks;
    private onBooksListSelectListener mCallback;

    public BooksListFragment() {

    }

    public static BooksListFragment newInstance(Bundle args) {
        Log.d(TAG, "newInstance");
        BooksListFragment f = new BooksListFragment();
        if(args != null)
            f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView...");
        View view = inflater.inflate(R.layout.bookslist_fragment, container, false);
        lvBooks = (ListView) view.findViewById(R.id.listview_books);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach...");
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (onBooksListSelectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onBooksListSelectListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");

        where = getArguments().getString(MainActivity.TAG_WHERE);
        orderBy = getArguments().getString(MainActivity.TAG_ORDERBY);
        displayList();

    }

    // Container Activity must implement this interface
    public interface onBooksListSelectListener {
        public void onBookSelected(int position);
    }

    private void displayList() {
        LibroModel model = new LibroModel(getActivity());
        ArrayList<Libro> data = model.getLibros(where, orderBy);

        if(data.size() > 0) {
            LibroAdapter adapter = new LibroAdapter(getActivity(), data);
            lvBooks.setAdapter(adapter);
        }

        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Notify the parent activity of selected item
                mCallback.onBookSelected(position);
            }
        });

    }


}