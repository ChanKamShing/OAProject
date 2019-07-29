package com.cjs.util.pager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class PagerTag extends SimpleTagSupport {
    //定义请求URL中的占位符常量
    private static final String TAG = "{0}";

    private int pageIndex;
    private int pageSize;
    private int recordCount;
    private int totalPage = 0;

    private String submitUrl;
    //** 样式
    private String style = "sabrosus";

    @Override
    public void doTag() throws JspException, IOException {
        System.out.println("====================");
        System.out.println("-"+pageIndex+"-"+pageSize);

        StringBuilder res = new StringBuilder();

        res.append("<center>\n" +
                "\t\t<p stytle=\"text-align:center;\">\n" +
                "\t\t\t<!-- 设计导航 -->\n" +
                "\t\t\t<nav class=\"nav form-inline\">\n" +
                "\t\t\t<ul class=\"pagination alin\">");
        //拼接中间的页码
        StringBuilder str = new StringBuilder();

        if (recordCount > 0) {
            totalPage = (this.recordCount - 1) / this.pageSize + 1;
            //判断上一页或下一页需不需要加a标签
            if (this.pageIndex == 1) {
                str.append("<li class=\"disabled\"><a href=\"#\">上一页</a></li>");
                //计算中间的页码
                this.calcPage(str);

                //下一页需不需要a标签
                if (this.pageIndex == totalPage) {
                    str.append("<li class=\"disabled\"><a href=\"#\">下一页</a></li>");
                } else {
                    String tempUrl = this.submitUrl.replace(TAG, String.valueOf(pageIndex + 1));
                    str.append("<li><a href='" + tempUrl + "'>下一页</a></li>");
                }
            } else if (this.pageIndex == totalPage) {
                String tempUrl = this.submitUrl.replace(TAG, String.valueOf(pageIndex - 1));
                str.append("<li><a href='" + tempUrl + "'>上一页</a></li>");
                this.calcPage(str);
                str.append("<li class=\"disabled\"><a href=\"#\">下一页</a></li>");
            } else {
                String tempUrl = this.submitUrl.replace(TAG, String.valueOf(pageIndex - 1));
                str.append("<li><a href='" + tempUrl + "'>上一页</a></li>");
                this.calcPage(str);
                tempUrl = this.submitUrl.replace(TAG, String.valueOf(pageIndex + 1));
                str.append("<li><a href='" + tempUrl + "'>下一页</a></li>");
            }
            res.append(str);

            //开始条数
            int startNum = (this.pageIndex - 1) * this.pageSize + 1;
            //结束条数
            int endNum = (this.pageIndex == this.totalPage) ? this.recordCount : this.pageIndex * this.pageSize;

            res.append("<li><a style=\"background-color:#D4D4D4;\" href=\"#\">共<font color='red'>"
                    + this.recordCount + "</font>条记录，当前显示" + startNum + "~" + endNum + "条记录</a>&nbsp;</li>");

            res.append("<div class=\"input-group\">\n" +
                    "\t\t\t\t\t\t\t\t\t     <input id='pager_jump_page_size' value='" + this.pageIndex + "' type=\"text\" " +
                    "style = \"width:60px;text-align:center;\" class=\"form-control\" placeholder=\"" + this.pageIndex + "\">\n" +
                    "\t\t\t\t\t\t\t\t\t     <span class=\"input-group-btn\">\n" +
                    "\t\t\t\t\t\t\t\t\t     <button class=\"btn btn-info\" id='pager_jump_btn' type=\"button\">GO</button>\n" +
                    "\t\t\t\t\t\t\t\t\t     </span>\n" +
                    "\t\t\t\t\t    \t\t\t\t</div>");
            res.append("<script type=\"text/javascript\">\n" +
                    "        document.getElementById('pager_jump_btn').onclick = function () {\n" +
                    "            var page_size = document.getElementById('pager_jump_page_size').value;\n" +
                    "            if (!/^[1-9]\\d*$/.test(page_size) || page_size < 1 || page_size > "+this.totalPage+") {\n" +
                    "                alert('请输入[1~"+this.totalPage+"]之间的页码！');\n" +
                    "            } else {\n" +
                    "                var submit_url = '"+this.submitUrl+"';\n" +
                    "                window.location = submit_url.replace('"+TAG+"', page_size);\n" +
                    "            }\n" +
                    "        }\n" +
                    "    </script>");
        } else {
            res.append("<li><a style=\"background-color: #d4d4d4;\" href=\"#\">总共<font color=\"red\">0</font>条记录，当前显示0-0条记录</a>&nbsp;</li>");
        }

        res.append("</ul></nav></p></center>");
        this.getJspContext().getOut().print(res.toString());
    }

    private void calcPage(StringBuilder str) {
        //如果总页数小于11，则一次性显示出来
        if (this.totalPage <= 11) {
            for (int i = 1; i < this.totalPage; i++) {
                if (this.pageIndex == i) {
                    str.append("<li class=\"active\"><a href=\"#\">" + i + "</a></li>");
                } else {
                    String tempUrl = this.submitUrl.replace(TAG, String.valueOf(i));
                    str.append("<li><a href=\"" + tempUrl + "\">" + i + "</a></li>");
                }
            }
        } else {
            //当前页靠近首页
            if (this.pageIndex <= 8) {
                for (int i = 1; i <= 10; i++) {
                    if (this.pageIndex == i) {
                        str.append("<li class=\"active\"><a href=\"#\">" + i + "</a></li>");
                    } else {
                        String tempUrl = this.submitUrl.replace(TAG, String.valueOf(i));
                        str.append("<li><a href=\"" + tempUrl + "\">" + i + "</a></li>");
                    }
                }
                str.append("<li><a href=\"#\">...</a></li>");
                String tempUrl = this.submitUrl.replace(TAG, String.valueOf(totalPage));
                str.append("<li><a href=\"" + tempUrl + "\">" + totalPage + "</a></li>");
            }
            //当前页靠近尾页
            else if (this.pageIndex + 8 >= this.totalPage) {
                String tempUrl = this.submitUrl.replace(TAG, String.valueOf(1));
                str.append("<li><a href=\"" + tempUrl + "\">" + 1 + "</a></li>");
                str.append("<li><a href=\"#\">...</a></li>");

                for (int i = this.totalPage - 10; i <= this.totalPage; i++) {
                    if (this.pageIndex == i) {
                        str.append("<li class=\"active\"><a href=\"#\">" + i + "</a></li>");
                    } else {
                        tempUrl = this.submitUrl.replace(TAG, String.valueOf(i));
                        str.append("<li ><a href=\"" + tempUrl + "\">" + i + "</a></li>");
                    }
                }
            }
            //中间页码
            else {
                String tempUrl = this.submitUrl.replace(TAG, String.valueOf(1));
                str.append("<li><a href=\"" + tempUrl + "\">" + 1 + "</a></li>");
                str.append("<li><a href=\"#\">...</a></li>");
                for (int i = this.pageIndex - 4; i <= this.pageIndex + 4; i++) {
                    if (this.pageIndex == i) {
                        str.append("<li class=\"active\"><a href=\"#\">" + i + "</a></li>");
                    } else {
                        tempUrl = this.submitUrl.replace(TAG, String.valueOf(i));
                        str.append("<li><a href=\"" + tempUrl + "\">" + i + "</a></li>");
                    }
                }
                str.append("<li><a href=\"#\">...</a></li>");
                tempUrl = this.submitUrl.replace(TAG, String.valueOf(this.totalPage));
                str.append("<li><a href=\"" + tempUrl + "\">" + this.totalPage + "</a></li>");
            }
        }
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getSubmitUrl() {
        return submitUrl;
    }

    public void setSubmitUrl(String submitUrl) {
        this.submitUrl = submitUrl;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
