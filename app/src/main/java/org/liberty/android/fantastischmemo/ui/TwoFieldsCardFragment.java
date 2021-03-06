/*
Copyright (C) 2013 Haowen Ning

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

 */
package org.liberty.android.fantastischmemo.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.common.BaseFragment;
import org.liberty.android.fantastischmemo.entity.Setting;
import org.liberty.android.fantastischmemo.entity.Card;

import java.sql.SQLException;

/**
 * This fragment is for a card of two fields, field1 is the upper field and filed2
 * is the lower field with a separator that divide field1 and field2.
 */
public class TwoFieldsCardFragment extends BaseFragment {

    /**
     * The key for the input a list of CardFragemnt.Builder that is used to build field1.
     * Input type is CardFragment.Builder[]
     */
    public static final String EXTRA_FIELD1_CARD_FRAGMENT_BUILDERS = "field1CardFragmentBuilders";

    /**
     * The key for the input a list of CardFragemnt.Builder that is used to build field2.
     * Input type is CardFragment.Builder[]
     */
    public static final String EXTRA_FIELD2_CARD_FRAGMENT_BUILDERS = "field2CardFragmentBuilders";

    /**
     * The key for the input a list of CardFragemnt.Builder that is used to build field3.
     * Input type is CardFragment.Builder[]
     */
    public static final String EXTRA_FIELD3_CARD_FRAGMENT_BUILDERS = "field3CardFragmentBuilders";

    /**
     * The initial position of the side in a multi-sided field for field1.
     * Input type is int.
     */
    public static final String EXTRA_FIELD1_INITIAL_POSITION = "field1InitialPosition";

    /**
     * The initial position of the side in a multi-sided field for field2.
     * Input type is int.
     */
    public static final String EXTRA_FIELD2_INITIAL_POSITION = "field2InitialPosition";

    /**
     * The initial position of the side in a multi-sided field for field3.
     * Input type is int.
     */
    public static final String EXTRA_FIELD3_INITIAL_POSITION = "field3InitialPosition";

    /**
     * The Ratio of the field1 and field2
     * Input type is int.
     */
    public static final String EXTRA_QA_RATIO = "qaRatio";

    /**
     * The color the separator line of field1 and field2.
     * Input type is int that represent an RGBA color.
     */
    public static final String EXTRA_SEPARATOR_COLOR= "separatorColor";

    private CardFragment.Builder[] field1CardFragmentBuilders;

    private CardFragment.Builder[] field2CardFragmentBuilders;

    private CardFragment.Builder[] field3CardFragmentBuilders;

    private ViewPager field1CardPager;

    private ViewPager field2CardPager;

    private ViewPager field3CardPager;

    private int qaRatio;

    private int separatorColor;

    private int field1InitialPosition = 0;

    private int field2InitialPosition = 0;

    private int field3InitialPosition = 0;

    private ImageButton favouriteBtn;

    private boolean quizMode = false;

    public TwoFieldsCardFragment() { }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        // Need to convert the array type to Serializable to CardFragment.Builder
        Object[] array1 = (Object[]) getArguments().getSerializable(EXTRA_FIELD1_CARD_FRAGMENT_BUILDERS);
        Object[] array2 = (Object[]) getArguments().getSerializable(EXTRA_FIELD2_CARD_FRAGMENT_BUILDERS);
        Object[] array3 = (Object[]) getArguments().getSerializable(EXTRA_FIELD3_CARD_FRAGMENT_BUILDERS);

        field1CardFragmentBuilders = new CardFragment.Builder[array1.length];
        field2CardFragmentBuilders = new CardFragment.Builder[array2.length];
        field3CardFragmentBuilders = new CardFragment.Builder[array3.length];

        for (int i = 0; i < array1.length;  i++) {
            field1CardFragmentBuilders[i] = (CardFragment.Builder) array1[i];
        }

        for (int i = 0; i < array2.length;  i++) {
            field2CardFragmentBuilders[i] = (CardFragment.Builder) array2[i];
        }

        for (int i = 0; i < array3.length;  i++) {
            field3CardFragmentBuilders[i] = (CardFragment.Builder) array3[i];
        }

        qaRatio = getArguments().getInt(EXTRA_QA_RATIO, Setting.DEFAULT_QA_RATIO);

        separatorColor = getArguments().getInt(EXTRA_SEPARATOR_COLOR, Setting.DEFAULT_SEPARATOR_COLOR);

        field1InitialPosition = getArguments().getInt(EXTRA_FIELD1_INITIAL_POSITION, 0);

        field2InitialPosition = getArguments().getInt(EXTRA_FIELD2_INITIAL_POSITION, 0);

        field3InitialPosition = getArguments().getInt(EXTRA_FIELD3_INITIAL_POSITION, 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.two_fields_card_layout, container, false);
        favouriteBtn = (ImageButton)v.findViewById(R.id.favourite_button);
        favouriteBtn.setOnClickListener(favouriteButtonHandler);
        boolean fav = ((QACardActivity)getActivity()).getCurrentCard().getFavourite();

        if (fav) {
            favouriteBtn.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else {
            favouriteBtn.setImageResource(android.R.drawable.btn_star_big_off);
        }

        field1CardPager = (ViewPager) v.findViewById(R.id.field1);
        field1CardPager.setAdapter(new FragmentStatePagerAdapter(
                getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return field1CardFragmentBuilders[position].build();
            }

            @Override
            public int getCount() {
                return field1CardFragmentBuilders.length;
            }
        });
        field2CardPager = (ViewPager) v.findViewById(R.id.field2);
        field2CardPager.setAdapter(new FragmentStatePagerAdapter(
                getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return field2CardFragmentBuilders[position].build();
            }

            @Override
            public int getCount() {
                return field2CardFragmentBuilders.length;
            }
        });
        field3CardPager = (ViewPager) v.findViewById(R.id.field3);
        field3CardPager.setAdapter(new FragmentStatePagerAdapter(
                getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return field3CardFragmentBuilders[position].build();
            }

            @Override
            public int getCount() {
                return field3CardFragmentBuilders.length;
            }
        });

        // Handle the QA ratio
        float qRatio = qaRatio;
        if (qRatio > 99.0f) {
            field2CardPager.setVisibility(View.GONE);
            field1CardPager
                    .setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT, 1.0f));
            field2CardPager
                    .setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT, 1.0f));
            field3CardPager
                    .setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT, 1.0f));
        } else if (qRatio < 1.0f) {
            field1CardPager.setVisibility(View.GONE);
            field1CardPager
                    .setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT, 1.0f));
            field2CardPager
                    .setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT, 1.0f));
            field3CardPager
                    .setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT, 1.0f));
        } else {
            field1CardPager.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                    qRatio));
            field2CardPager.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                    100f - qRatio));
            field3CardPager.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                    100f - qRatio));
        }

        // Separator color
        View separatorLine = v.findViewById(R.id.horizontal_line);
        separatorLine.setBackgroundColor(separatorColor);

        field1CardPager.setCurrentItem(field1InitialPosition);
        field2CardPager.setCurrentItem(field2InitialPosition);
        field3CardPager.setCurrentItem(field3InitialPosition);

        loadPrefs();

        return v;

    }

    public void loadPrefs() {
        SharedPreferences sp = getActivity().getSharedPreferences("AppPref", 0);
        SharedPreferences qp = getActivity().getSharedPreferences("QuizPref", 0);
        boolean hintToggleValue = sp.getBoolean("hintToggleCheck", true);
        quizMode = qp.getBoolean("quizMode",false);

        if(hintToggleValue == false || quizMode) {
            field3CardPager.setVisibility(View.GONE);
        } else {
            field3CardPager.setVisibility(View.VISIBLE);
        }
    }
    View.OnClickListener favouriteButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {

            boolean fav = ((QACardActivity)getActivity()).getCurrentCard().getFavourite();
            Card card = ((QACardActivity)getActivity()).getCurrentCard();
            String dbPath = ((QACardActivity)getActivity()).getDbPath();
            if(!dbPath.equals("/sdcard/favourites.db") && !quizMode) {
                if (fav) {
                    favouriteBtn.setImageResource(android.R.drawable.btn_star_big_off);
                    ((QACardActivity)getActivity()).getDao().updateFavourite(card, false);
                    ((QACardActivity)getActivity()).unfavouriteCard();

                } else {
                    favouriteBtn.setImageResource(android.R.drawable.btn_star_big_on);
                    ((QACardActivity)getActivity()).getDao().updateFavourite(card, true);
                    ((QACardActivity)getActivity()).favouriteCard();

                }
            }



        }
    };
}

