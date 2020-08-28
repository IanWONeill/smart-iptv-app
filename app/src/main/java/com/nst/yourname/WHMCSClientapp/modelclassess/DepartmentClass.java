package com.nst.yourname.WHMCSClientapp.modelclassess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DepartmentClass {
    @SerializedName("departments")
    @Expose
    private Departments departments;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("totalresults")
    @Expose
    private Integer totalresults;

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public Integer getTotalresults() {
        return this.totalresults;
    }

    public void setTotalresults(Integer num) {
        this.totalresults = num;
    }

    public Departments getDepartments() {
        return this.departments;
    }

    public void setDepartments(Departments departments2) {
        this.departments = departments2;
    }

    public class Departments {
        @SerializedName("department")
        @Expose
        private List<Department> department = null;

        public Departments() {
        }

        public List<Department> getDepartment() {
            return this.department;
        }

        public void setDepartment(List<Department> list) {
            this.department = list;
        }

        public class Department {
            @SerializedName("awaitingreply")
            @Expose
            private String awaitingreply;
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("opentickets")
            @Expose
            private String opentickets;

            public Department() {
            }

            public String getId() {
                return this.id;
            }

            public void setId(String str) {
                this.id = str;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }

            public String getAwaitingreply() {
                return this.awaitingreply;
            }

            public void setAwaitingreply(String str) {
                this.awaitingreply = str;
            }

            public String getOpentickets() {
                return this.opentickets;
            }

            public void setOpentickets(String str) {
                this.opentickets = str;
            }
        }
    }
}
