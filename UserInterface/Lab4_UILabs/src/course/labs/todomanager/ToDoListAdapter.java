package course.labs.todomanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends BaseAdapter {

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";

	public ToDoListAdapter(Context context) {

		mContext = context;

	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// Create a View for the ToDoItem at specified position
	// Remember to check whether convertView holds an already allocated View
	// before created a new View.
	// Consider using the ViewHolder pattern to make scrolling more efficient
	// See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolderItem;
		final ToDoItem toDoItem = mItems.get(position);

		if (convertView == null) {
			final LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.todo_item, parent, false);

			viewHolderItem = new ViewHolder();

			viewHolderItem.titleView = (TextView) convertView.findViewById(R.id.titleView);
			viewHolderItem.statusView = (CheckBox) convertView.findViewById(R.id.statusCheckBox);
			viewHolderItem.priorityView = (TextView) convertView.findViewById(R.id.priorityView);
			viewHolderItem.dateView = (TextView) convertView.findViewById(R.id.dateView);
		} else {
			viewHolderItem = (ViewHolder) convertView.getTag();
		}

		if (toDoItem != null) {
			viewHolderItem.titleView.setText(toDoItem.getTitle());

			viewHolderItem.statusView.setChecked(toDoItem.getStatus().equals(ToDoItem.Status.DONE));

			viewHolderItem.statusView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
											 boolean isChecked) {
					toDoItem.setStatus((isChecked) ? ToDoItem.Status.DONE : ToDoItem.Status.NOTDONE);
					buttonView.setChecked(isChecked);
				}
			});

			viewHolderItem.priorityView.setText(toDoItem.getPriority().toString().toLowerCase());
			viewHolderItem.dateView.setText(ToDoItem.FORMAT.format(toDoItem.getDate()));
		}

		convertView.setTag(viewHolderItem);

		// Return the View you just created
		return convertView;

	}

	static class ViewHolder {
		TextView titleView;
		CheckBox statusView;
		TextView priorityView;
		TextView dateView;
	}
}
