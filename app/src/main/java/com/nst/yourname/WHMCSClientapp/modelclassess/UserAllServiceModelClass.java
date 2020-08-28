package com.nst.yourname.WHMCSClientapp.modelclassess;

import com.anjlab.android.iab.v3.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UserAllServiceModelClass {
    @SerializedName("products")
    @Expose
    private Products products;
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

    public Products getProducts() {
        return this.products;
    }

    public void setProducts(Products products2) {
        this.products = products2;
    }

    public class Products {
        @SerializedName("product")
        @Expose
        private List<Product> product = null;

        public Products() {
        }

        public List<Product> getProduct() {
            return this.product;
        }

        public void setProduct(List<Product> list) {
            this.product = list;
        }

        public class Product {
            @SerializedName("configoptions")
            @Expose
            private Configoptions configoptions;
            @SerializedName("customfields")
            @Expose
            private Customfields customfields;
            @SerializedName(Constants.RESPONSE_DESCRIPTION)
            @Expose
            private String description;
            @SerializedName("gid")
            @Expose
            private String gid;
            @SerializedName("module")
            @Expose
            private String module;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("paytype")
            @Expose
            private String paytype;
            @SerializedName("pid")
            @Expose
            private String pid;
            @SerializedName("pricing")
            @Expose
            private Pricing pricing;
            @SerializedName("stockcontrol")
            @Expose
            private String stockcontrol;
            @SerializedName("stocklevel")
            @Expose
            private String stocklevel;
            @SerializedName("type")
            @Expose
            private String type;

            public Product() {
            }

            public String getPid() {
                return this.pid;
            }

            public void setPid(String str) {
                this.pid = str;
            }

            public String getGid() {
                return this.gid;
            }

            public void setGid(String str) {
                this.gid = str;
            }

            public String getType() {
                return this.type;
            }

            public void setType(String str) {
                this.type = str;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }

            public String getDescription() {
                return this.description;
            }

            public void setDescription(String str) {
                this.description = str;
            }

            public String getModule() {
                return this.module;
            }

            public void setModule(String str) {
                this.module = str;
            }

            public String getPaytype() {
                return this.paytype;
            }

            public void setPaytype(String str) {
                this.paytype = str;
            }

            public String getStockcontrol() {
                return this.stockcontrol;
            }

            public void setStockcontrol(String str) {
                this.stockcontrol = str;
            }

            public String getStocklevel() {
                return this.stocklevel;
            }

            public void setStocklevel(String str) {
                this.stocklevel = str;
            }

            public Pricing getPricing() {
                return this.pricing;
            }

            public void setPricing(Pricing pricing2) {
                this.pricing = pricing2;
            }

            public Customfields getCustomfields() {
                return this.customfields;
            }

            public void setCustomfields(Customfields customfields2) {
                this.customfields = customfields2;
            }

            public Configoptions getConfigoptions() {
                return this.configoptions;
            }

            public void setConfigoptions(Configoptions configoptions2) {
                this.configoptions = configoptions2;
            }

            public class Pricing {
                @SerializedName("USD")
                @Expose
                private USD uSD;

                public Pricing() {
                }

                public USD getUSD() {
                    return this.uSD;
                }

                public void setUSD(USD usd) {
                    this.uSD = usd;
                }

                public class USD {
                    @SerializedName("annually")
                    @Expose
                    private String annually;
                    @SerializedName("asetupfee")
                    @Expose
                    private String asetupfee;
                    @SerializedName("biennially")
                    @Expose
                    private String biennially;
                    @SerializedName("bsetupfee")
                    @Expose
                    private String bsetupfee;
                    @SerializedName("monthly")
                    @Expose
                    private String monthly;
                    @SerializedName("msetupfee")
                    @Expose
                    private String msetupfee;
                    @SerializedName("prefix")
                    @Expose
                    private String prefix;
                    @SerializedName("qsetupfee")
                    @Expose
                    private String qsetupfee;
                    @SerializedName("quarterly")
                    @Expose
                    private String quarterly;
                    @SerializedName("semiannually")
                    @Expose
                    private String semiannually;
                    @SerializedName("ssetupfee")
                    @Expose
                    private String ssetupfee;
                    @SerializedName("suffix")
                    @Expose
                    private String suffix;
                    @SerializedName("triennially")
                    @Expose
                    private String triennially;
                    @SerializedName("tsetupfee")
                    @Expose
                    private String tsetupfee;

                    public USD() {
                    }

                    public String getPrefix() {
                        return this.prefix;
                    }

                    public void setPrefix(String str) {
                        this.prefix = str;
                    }

                    public String getSuffix() {
                        return this.suffix;
                    }

                    public void setSuffix(String str) {
                        this.suffix = str;
                    }

                    public String getMsetupfee() {
                        return this.msetupfee;
                    }

                    public void setMsetupfee(String str) {
                        this.msetupfee = str;
                    }

                    public String getQsetupfee() {
                        return this.qsetupfee;
                    }

                    public void setQsetupfee(String str) {
                        this.qsetupfee = str;
                    }

                    public String getSsetupfee() {
                        return this.ssetupfee;
                    }

                    public void setSsetupfee(String str) {
                        this.ssetupfee = str;
                    }

                    public String getAsetupfee() {
                        return this.asetupfee;
                    }

                    public void setAsetupfee(String str) {
                        this.asetupfee = str;
                    }

                    public String getBsetupfee() {
                        return this.bsetupfee;
                    }

                    public void setBsetupfee(String str) {
                        this.bsetupfee = str;
                    }

                    public String getTsetupfee() {
                        return this.tsetupfee;
                    }

                    public void setTsetupfee(String str) {
                        this.tsetupfee = str;
                    }

                    public String getMonthly() {
                        return this.monthly;
                    }

                    public void setMonthly(String str) {
                        this.monthly = str;
                    }

                    public String getQuarterly() {
                        return this.quarterly;
                    }

                    public void setQuarterly(String str) {
                        this.quarterly = str;
                    }

                    public String getSemiannually() {
                        return this.semiannually;
                    }

                    public void setSemiannually(String str) {
                        this.semiannually = str;
                    }

                    public String getAnnually() {
                        return this.annually;
                    }

                    public void setAnnually(String str) {
                        this.annually = str;
                    }

                    public String getBiennially() {
                        return this.biennially;
                    }

                    public void setBiennially(String str) {
                        this.biennially = str;
                    }

                    public String getTriennially() {
                        return this.triennially;
                    }

                    public void setTriennially(String str) {
                        this.triennially = str;
                    }
                }
            }

            public class Configoptions {
                @SerializedName("configoption")
                @Expose
                private List<Object> configoption = null;

                public Configoptions() {
                }

                public List<Object> getConfigoption() {
                    return this.configoption;
                }

                public void setConfigoption(List<Object> list) {
                    this.configoption = list;
                }
            }

            public class Customfields {
                @SerializedName("customfield")
                @Expose
                private List<Customfield> customfield = null;

                public Customfields() {
                }

                public List<Customfield> getCustomfield() {
                    return this.customfield;
                }

                public void setCustomfield(List<Customfield> list) {
                    this.customfield = list;
                }

                public class Customfield {
                    @SerializedName(Constants.RESPONSE_DESCRIPTION)
                    @Expose
                    private String description;
                    @SerializedName("id")
                    @Expose
                    private String id;
                    @SerializedName("name")
                    @Expose
                    private String name;
                    @SerializedName("required")
                    @Expose
                    private String required;

                    public Customfield() {
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

                    public String getDescription() {
                        return this.description;
                    }

                    public void setDescription(String str) {
                        this.description = str;
                    }

                    public String getRequired() {
                        return this.required;
                    }

                    public void setRequired(String str) {
                        this.required = str;
                    }
                }
            }
        }
    }
}
