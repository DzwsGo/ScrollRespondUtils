# ScrollRespondUtils
滑动响应工具类
目前支持RecyclerView和ScrollView的滑动响应

1、RecyclerView example

![image](https://github.com/UseLived/ScrollRespondUtils/blob/master/app/src/main/res/drawable/recyclerview.gif)

2、ScrollView example

![image](https://github.com/UseLived/ScrollRespondUtils/blob/master/app/src/main/res/drawable/scrollview.gif)


* 使用

`
//初始化
ScrollRespondUtils scrollRespondUtils = new ScrollRespondUtils(this);
//设置响应滑动的View
scrollRespondUtils.setRespondView(rlBottom);
//调用start响应
scrollRespondUtils.start(view,i,i1,i2,i3);
`

1、RecyclerView
`
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            scrollRespondUtils.start(dy);
        }
    });
    `

2、ScrollView
`
    scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
        @Override
        public void onScrollChange(View view, int i, int i1, int i2, int i3) {
            //调用start响应
            scrollRespondUtils.start(view,i,i1,i2,i3);
        }
    });
`
