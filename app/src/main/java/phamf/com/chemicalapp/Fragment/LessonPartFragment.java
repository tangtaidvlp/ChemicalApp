package phamf.com.chemicalapp.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import phamf.com.chemicalapp.CustomView.LessonHtmlViewCreator;
import phamf.com.chemicalapp.CustomView.LessonViewCreator;
import phamf.com.chemicalapp.R;

import static phamf.com.chemicalapp.CustomView.LessonHtmlViewCreator.DEVIDER;
import static phamf.com.chemicalapp.CustomView.LessonHtmlViewCreator.IMAGE;

public class LessonPartFragment extends Fragment {

    @BindView(R.id.board) public LinearLayout content_view;

    private static final String CONTENT = "Content";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        content_view = view.findViewById(R.id.board);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String content = bundle.getString(CONTENT, "");
            LessonViewCreator.ViewCreator viewCreator = new LessonViewCreator.ViewCreator(getContext(), content_view);
            viewCreator.addView(content);
        }
    }

    public static LessonPartFragment newInstance(String content) {
        LessonPartFragment fragment = new LessonPartFragment();
        Bundle args = new Bundle();
        args.putString(CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

}
class test {
    public static void main (String[ ] s) {
        String content = "200|100|Lessons/lesson1/image1.jpg";
        for (String ss : content.split("\\|")) {
            System.out.println(ss);
        }

    }
}
