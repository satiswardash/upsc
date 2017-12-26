package com.kortain.upsc.helpers;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.AppBarLayout;
import android.view.View;

import com.kortain.upsc.models.ConstraintRules;

import java.util.List;

/**
 * Created by satiswardash on 26/12/17.
 */

public class LayoutHelper {

    public static final int VISIBLE = 1;
    public static final int INVISIBLE = 0;
    private static LayoutHelper instance;

    private LayoutHelper() {

    }

    /**
     * Get Instance
     * @return
     */
    public static LayoutHelper getInstance(){
        if(instance == null){
            return new LayoutHelper();
        }
        else
            return instance;
    }

    /**
     * Reset AppBarLayout Visibility
     * @param param
     * @param layout
     */
    public void resetAppBarLayoutVisibility(int param, @NonNull AppBarLayout layout){
        if(param == INVISIBLE){
            if (layout.getVisibility() == View.VISIBLE) {
                layout.setVisibility(View.INVISIBLE);
            }
        }
        else if(param == VISIBLE){
            if (layout.getVisibility() == View.INVISIBLE) {
                layout.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Reset Layout Constraints
     * @param layout
     * @param rules
     */
    public void resetLayoutConstraints(@NonNull ConstraintLayout layout, @NonNull List<ConstraintRules> rules) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);
        for (ConstraintRules rule: rules) {
            constraintSet.connect(rule.getStartId(), rule.getStartSide(), rule.getEndId(), rule.getEndSide(), rule.getMargin());
        }
        constraintSet.applyTo(layout);
    }
}
