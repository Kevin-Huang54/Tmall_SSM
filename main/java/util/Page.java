package util;

public class Page {
    int start = 0;
    int count = 5;
    int total;
    String param = null; //用于产品等页面中存放后缀“&pid=3”等类似内容

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    //获取总共有多少页，jsp中需要调用
    public int getTotalPage() {
        int totalPage = 1;
        if (total % count == 0) {
            totalPage = total / count;
        } else {
            totalPage = total / count + 1;
        }
        if (total == 0) {
            totalPage = 1;
        }
        return totalPage;
    }

    //获取最后一页的起始项
    public int getLast() {
        int last;
        if (total % count == 0) {
            last = total - count;
        } else {
            last = total - total % count;
        }
        if (last < 0) {
            last = 0;
        }
        return last;
    }
}
