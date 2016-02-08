package com.example.trainee6.demorecyclerv;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trainee6 on 2/3/2016.
 */
public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.MyViewHolder>{

    private List<DataBeanDriver> driverList,orgList;
    private Context context;
    View v;
    private LayoutInflater mInflater;
    //private List<DataBeanDriver> newDriverList;

    public DriverAdapter(Context context, List<DataBeanDriver> models) {
        mInflater = LayoutInflater.from(context);
        driverList = new ArrayList<>(models);
        orgList = new ArrayList<>(models);
    }

    public DriverAdapter(Context context) {
        this.driverList = new ArrayList<>();
        orgList = new ArrayList<>();
    }

    public DriverAdapter(List<DataBeanDriver> driverList) {
        this.driverList = driverList;
        this.orgList=driverList;
    }

    public DataBeanDriver removeItem(int position) {
        final DataBeanDriver model = driverList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, DataBeanDriver model) {
        driverList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final DataBeanDriver model = driverList.remove(fromPosition);
        driverList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<DataBeanDriver> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<DataBeanDriver> newModels) {
        for (int i = driverList.size() - 1; i >= 0; i--) {
            final DataBeanDriver model = driverList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<DataBeanDriver> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final DataBeanDriver model = newModels.get(i);
            if (!orgList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<DataBeanDriver> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final DataBeanDriver model = newModels.get(toPosition);
            final int fromPosition = driverList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_row_new, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DataBeanDriver driver = driverList.get(position);

        holder.driverHours.setText(driver.getHour()+" Hours");
        holder.driverID.setText(driver.getId());
        holder.driverName.setText(driver.getName());
        holder.carNo.setText(driver.getCarno());
        holder.carModel.setText(driver.getCarmodel());
        holder.carCompany.setText(driver.getMobile());
        if(driver.getStatus() == 0)//green
        {
            v.setBackgroundColor(Color.parseColor("#529E32"));
            holder.driverHours.setTextColor(Color.parseColor("#529E32"));
        }
        else if(driver.getStatus() == 1)//orange
        {
            v.setBackgroundColor(Color.parseColor("#FE7E13"));
            holder.driverHours.setTextColor(Color.parseColor("#FE7E13"));
        }
        else//red
        {
            v.setBackgroundColor(Color.parseColor("#E33131"));
            holder.driverHours.setTextColor(Color.parseColor("#E33131"));
        }
        v.bringToFront();

    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView driverID, driverHours, driverName,carNo,carModel,carCompany;
        public Button btnEmail,btnMessage,btnCall,btnGmail;
        DataBeanDriver driver1;

        public MyViewHolder(final View view) {
            super(view);
            context=view.getContext();
            driverID = (TextView) view.findViewById(R.id.lbl_id);
            driverHours = (TextView) view.findViewById(R.id.lbl_hours);
            driverName = (TextView) view.findViewById(R.id.lbl_name);
            carNo=(TextView) view.findViewById(R.id.lbl_carno);
            carModel=(TextView) view.findViewById(R.id.lbl_carmodel);
            carCompany=(TextView) view.findViewById(R.id.lbl_carcmpny);
            v=view.findViewById(R.id.view);

            btnMessage= (Button) view.findViewById(R.id.btnMessage);
            btnMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    driver1 = driverList.get(getAdapterPosition());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("sms:" + driver1.getMobile()));
                    view.getContext().startActivity(intent);
                }
            });
            btnGmail= (Button) view.findViewById(R.id.btnGmail);
            btnEmail= (Button) view.findViewById(R.id.btnMail);
            btnCall= (Button) view.findViewById(R.id.btnCall);
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    driver1 = driverList.get(getAdapterPosition());
                    //Toast.makeText(view.getContext(), "Call clicked at pos: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + driver1.getMobile()));
                    view.getContext().startActivity(intent);
                }
            });


        }
    }
}
