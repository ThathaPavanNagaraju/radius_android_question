package com.radius;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.radius.Entities.Exclusion;
import com.radius.Entities.Facilities;
import com.radius.Entities.Facility;
import com.radius.Entities.Option;
import com.radius.Models.MyRadioGroup;

import java.util.List;

/**
 * Created by Pavan on 09-08-2018.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private final Context mContext;
    private final Facilities mFacilities;
    public ListAdapter(Context context, Facilities facilities) {
        mContext = context;
        mFacilities = facilities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvFacilityName.setText(mFacilities.getFacilities().get(position).getName());
        holder.rgFacility.removeAllViews();
        final List<Option> options = mFacilities.getFacilities().get(position).getOptions();
        for(int i=0;i<options.size();i++){
            LinearLayout ll = new LinearLayout(mContext);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ImageView imageView = new ImageView(mContext);
            imageView.setImageDrawable(Utils.getDrawableFromName(mContext,Utils.replaceUnderScores(options.get(i).getIcon())));
            imageView.setLayoutParams(new LinearLayout.LayoutParams(100,100));
            final RadioButton radioButton = new RadioButton(mContext);
            radioButton.setText(options.get(i).getName());
            ll.addView(imageView);
            ll.addView(radioButton);
            holder.rgFacility.addView(ll);
            if(options.get(i).isDisabled()){
                ll.setVisibility(View.GONE);
            }
            else {
                ll.setVisibility(View.VISIBLE);
            }
            final int finalI = i;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedFacilityOptions(mFacilities.getFacilities().get(position).getFacilityId(),options.get(finalI).getId());
                    radioButton.setChecked(true);
                }
            });
        }
    }

    private void selectedFacilityOptions(String facilityId, String option_id) {
        for(int i=0;i<mFacilities.getFacilities().size();i++){
            Facility facility = mFacilities.getFacilities().get(i);
            if(facility.getFacilityId().equalsIgnoreCase(facilityId)){
                continue;
            }
            else {
                List<List<Exclusion>> exclusionList = mFacilities.getExclusions();
                for(int j=0;j<exclusionList.size();j++){
                    List<Exclusion> exclusionPair = exclusionList.get(j);
                    if(exclusionPair.get(0).getOptionsId().equalsIgnoreCase(option_id) && exclusionPair.get(0).getFacilityId().equalsIgnoreCase(facilityId)){
                        disableOption(exclusionPair.get(1).getFacilityId(),exclusionPair.get(1).getOptionsId());
                    }
                    else if (exclusionPair.get(1).getOptionsId().equalsIgnoreCase(option_id) && exclusionPair.get(1).getFacilityId().equalsIgnoreCase(facilityId)){
                        disableOption(exclusionPair.get(0).getFacilityId(),exclusionPair.get(0).getOptionsId());
                    }
                }
            }
        }
    }

    //make the option invisible
    private void disableOption(String facilityId, String optionsId) {
        for(int i=0;i<mFacilities.getFacilities().size();i++){
            if(mFacilities.getFacilities().get(i).getFacilityId().equalsIgnoreCase(facilityId)){
                Facility facility = mFacilities.getFacilities().get(i);
                for(int j=0;j<facility.getOptions().size();j++){
                    if(facility.getOptions().get(j).getId().equalsIgnoreCase(optionsId)){
                        facility.getOptions().get(j).setDisabled(true);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFacilities.getFacilities().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFacilityName;
        private final MyRadioGroup rgFacility;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFacilityName = itemView.findViewById(R.id.tv_facility_name);
            rgFacility = itemView.findViewById(R.id.rg_facility);
        }
    }
}
