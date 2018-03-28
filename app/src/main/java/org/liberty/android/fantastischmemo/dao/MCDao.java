package org.liberty.android.fantastischmemo.dao;


import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;

import java.util.List;

public interface MCDao extends HelperDao<MultipleChoiceCard, Integer> {

 List<MultipleChoiceCard> getAllMCCards();

 MultipleChoiceCard getMCCard(long id);

 MultipleChoiceCard addMCCard(MultipleChoiceCard mcCard);

 List<MultipleChoiceCard> deleteMCCard(MultipleChoiceCard mcCard);

 MultipleChoiceCard getNextMCCard(MultipleChoiceCard mcCard);

 MultipleChoiceCard getPrevMCCard(MultipleChoiceCard mcCard);

 AnyMemoDBOpenHelper getHelper();

 void setHelper(AnyMemoDBOpenHelper helper);

}
