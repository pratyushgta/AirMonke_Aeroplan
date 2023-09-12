package com.example.novemberechonew.Main.Hub;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.novemberechonew.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class HubFragment extends Fragment {

    private ViewPager2 viewPager;
    private CardAdapter cardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hub, container, false);

        viewPager = view.findViewById(R.id.hub_viewPager);

        // Create a list of CardItems
        List<CardItem> cardItems = new ArrayList<>();
        cardItems.add(new CardItem(R.drawable.airmonke_logo_only, "Card 1 Text", "Button 1"));
        cardItems.add(new CardItem(R.drawable.airmonke_logo_only, "Card 2 Text", "Button 2"));
        cardItems.add(new CardItem(R.drawable.airmonke_logo_only, "Card 3 Text", "Button 3"));
        cardItems.add(new CardItem(R.drawable.airmonke_logo_only, "Card 4 Text", "Button 4"));

        cardAdapter = new CardAdapter(requireContext(), cardItems);
        viewPager.setAdapter(cardAdapter);

        viewPager.setOffscreenPageLimit(cardItems.size());
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(getResources().getDimensionPixelOffset(R.dimen.page_margin)));
        viewPager.setPageTransformer(transformer);


//        CompositePageTransformer transformer = new CompositePageTransformer();
//        transformer.addTransformer(new MarginPageTransformer(40));
//        transformer.addTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float r = 1 - Math.abs(position);
//                page.setScaleY(r);
//            }
//        });
//
//        viewPager.setPageTransformer(transformer);

        return view;
    }
}

class CardItem {
    private int imageResId;
    private String text;
    private String buttonText;

    public CardItem(int imageResId, String text, String buttonText) {
        this.imageResId = imageResId;
        this.text = text;
        this.buttonText = buttonText;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }

    public String getButtonText() {
        return buttonText;
    }
}

class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private Context context;
    private List<CardItem> cardItems;

    public CardAdapter(Context context, List<CardItem> cardItems) {
        this.context = context;
        this.cardItems = cardItems;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_hub_carditem, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem cardItem = cardItems.get(position);

        holder.imageView.setImageResource(cardItem.getImageResId());
        holder.textView.setText(cardItem.getText());
        holder.button.setText(cardItem.getButtonText());
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        MaterialButton button;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.hub_card_image);
            textView = itemView.findViewById(R.id.hub_card_text);
            button = itemView.findViewById(R.id.hub_button);
        }
    }
}


