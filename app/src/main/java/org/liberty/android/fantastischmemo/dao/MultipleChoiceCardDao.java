package org.liberty.android.fantastischmemo.dao;


import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;

import java.util.List;

public interface MultipleChoiceCardDao extends HelperDao<MultipleChoiceCard, Integer> {

 List<MultipleChoiceCard> getAllMultipleChoiceCards();

 MultipleChoiceCard getMultipleChoiceCard(long id);

 MultipleChoiceCard addMultipleChoiceCard(MultipleChoiceCard mcCard);

 void deleteMultipleChoiceCard(MultipleChoiceCard mcCard);

 MultipleChoiceCard getNextMultipleChoiceCard(MultipleChoiceCard mcCard);

 MultipleChoiceCard getPrevMultipleChoiceCard(MultipleChoiceCard mcCard);

 void updateMultipleChoiceCard(MultipleChoiceCard mcCard);

 AnyMemoDBOpenHelper getHelper();

 void setHelper(AnyMemoDBOpenHelper helper);

}
