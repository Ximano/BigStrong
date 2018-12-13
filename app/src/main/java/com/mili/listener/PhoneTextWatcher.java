package com.mili.listener;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


/**
 * @dec   :
 * @author: big strong
 * @date  : 2018/12/12
 */
public class PhoneTextWatcher implements TextWatcher
{
    private EditText editText;
    private boolean isDelete;
    private int lastContentLength;

    public PhoneTextWatcher (EditText editText)
    {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        /* StringBuffer sb = new StringBuffer(s);
         //是否为输入状态
         isDelete = s.length() > lastContentLength ? false : true;

         //输入是第4，第9位，这时需要插入空格
         if (!isDelete && (s.length() == 4 || s.length() == 9))
         {
             if (s.length() == 4)
             {
                 sb.insert(3, " ");
             } else
             {
                 sb.insert(8, " ");
             }
             setContent(sb);
         }

         //删除的位置到4，9时，剔除空格
         if (isDelete && (s.length() == 4 || s.length() == 9))
         {
             sb.deleteCharAt(sb.length() - 1);
             setContent(sb);
         }

         lastContentLength = sb.length();*/
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        //需求是130 1234 4567，中间第4个数字和第5个数字空格前面加空格
        StringBuffer sb = new StringBuffer(s);
        //StringBuffer.length()是长度，所以下标从1开始
        //字符数组第4位如果不是空格字符，就在他前面插一个空格字符
        if (s.length() >= 4) {
            char[] chars = s . toString ().toCharArray();
            //数字下标是从0开始
            if (chars[3] != ' ') {
                sb.insert(3, ' ');
                setContent(sb);
            }
        }

        if (s.length() >= 9) {
            char[] chars = s . toString ().toCharArray();
            //因为第4位加了一个空格，所以第8位数字，就是字符数组的第9位，下标是8。
            if (chars[8] != ' ') {
                sb.insert(8, ' ');
                setContent(sb);
            }
        }
    }

    /**
     * 添加或删除空格EditText的设置
     */
    private void setContent(StringBuffer sb)
    {
        editText.setText(sb.toString());
        //移动光标到最后面
        editText.setSelection(sb.length());
    }
}