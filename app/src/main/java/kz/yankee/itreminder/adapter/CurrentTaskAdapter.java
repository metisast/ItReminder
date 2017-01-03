package kz.yankee.itreminder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kz.yankee.itreminder.R;
import kz.yankee.itreminder.Utils;
import kz.yankee.itreminder.model.Item;
import kz.yankee.itreminder.model.ModelTask;

/**
 * Адаптер для отображения текущих задач
 */
/**
 * Класс адаптера наследуется от RecyclerView.Adapter с указанием класса, который будет
 * хранить ссылки на виджеты элемента списка, т.е. класса, имплементирующего ViewHolder.
 * В нашем случае класс объявлен внутри класса адаптера.
 */

public class CurrentTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item> items = new ArrayList<>();

    private static final int TYPE_TASK = 0;
    private static final int TYPE_SEPARATOR = 1;

    public Item getItem(int position){
        return items.get(position);
    }

    public void addItem(Item item){
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int location, Item item){
        items.add(location, item);
        notifyItemInserted(location);
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Определяем тип задачи
        switch (viewType){
            case TYPE_TASK:
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.model_task, parent, false);
                TextView title = (TextView) v.findViewById(R.id.tvTaskTitle);
                TextView date = (TextView) v.findViewById(R.id.tvTaskDate);

                return new TaskViewHolder(v, title, date);
            default:
                return null;
        }
    }

    /**
     * Заполнение виджетов View данными из элемента списка с номером position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);

        if(item.isTask()) {
            holder.itemView.setEnabled(true);
            ModelTask task = (ModelTask) item;
            TaskViewHolder taskViewHolder = (TaskViewHolder) holder;

            taskViewHolder.title.setText(task.getTitle());
            if (task.getDate() != 0){
                taskViewHolder.date.setText(Utils.getFullDate(task.getDate()));
            }
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).isTask()){
            return TYPE_TASK;
        }else{
            return TYPE_SEPARATOR;
        }
    }

    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    private class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView date;

        public TaskViewHolder(View itemView, TextView title, TextView date) {
            super(itemView);

            this.title = title;
            this.date = date;
        }

    }
}
