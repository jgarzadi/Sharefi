package com.sharefi.srcit.sharefi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sharefi.srcit.sharefi.R;
import com.sharefi.srcit.sharefi.models.Connection;

import org.w3c.dom.Text;

import java.util.List;

public class ConnectionsAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Connection> connections;

    public ConnectionsAdapter(Context context, int layout, List<Connection> connections) {
        this.context = context;
        this.layout = layout;
        this.connections = connections;
    }

    @Override
    public int getCount() {
        return this.connections.size();
    }

    @Override
    public Object getItem(int position) {
        return this.connections.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View v = convertView;

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v = layoutInflater.inflate(R.layout.list_item_connections, null);

        Connection conn = connections.get(position);

        if (conn != null) {
            TextView textViewWifiName = (TextView) v.findViewById(R.id.textView_WifiName);
            textViewWifiName.setText(conn.getName());
        }

        return v;
    }
}
