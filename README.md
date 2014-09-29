使用
====

类似系统自己的AlertDialog的用法.只是把AlertDialog变成CustomAlertDialog就行了。

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Title");
        builder.setMessage(R.string.message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
            ...
        });
        builder.setNegativeButton("Cancel", null);


如已有以上的代码，则只需要将第一句builder的声明换成CustomAlertDialog即可,其它方法兼容.

        CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(activity);
        builder.setTitle("Title");
        builder.setMessage(R.string.message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
            ...
        });
        builder.setNegativeButton("Cancel", null);

Screenshot
==========

<img src="/screenshot/p1.png" alt="main" title="p1" width="480" height="800" /> <img src="/screenshot/p2.png" alt="main" title="p2" width="480" height="800" />
<img src="/screenshot/p3.png" alt="main" title="p3" width="480" height="800" />

目录
====

想在改Dialog的布局效果，直接调整res里面以"cad__"开头的文件即可.

        .
        ├── drawable
        │   ├── cad__item_list_selector.xml     列表的点击效果
        │   └── cad__progress.xml   水平进度的效果
        ├── drawable-hdpi
        │   └── cad__bg_dialog_action.9.png     button容器的背景
        ├── layout
        │   ├── cad__custom_dialog_listview.xml     dialog的list
        │   ├── cad__custom_dialog.xml              dialog的布局
        │   ├── cad__custom_progress_cycle.xml      dialog的圆形进度
        │   ├── cad__custom_progress_hori.xml       dialog的水平进度
        │   ├── cad__select_dialog_item.xml         dialog中list的item
        │   └── cad__select_dialog_singlechoice.xml dialog中list的单选item
        └── values
            ├── cad__colors.xml                 dialog里面用到的一些颜色
            ├── cad__custom_dialog_style.xml    dialog的style,主要是Text和Button
            ├── cad__custom_dialog_theme.xml    主题，暂没什么用
            └── cad__dimens.xml                 dalog里面的一些大小值


修改的时候，不要改id,如果有删除view这样的操作，最好，自己修改CustomAlertDialog的源码.