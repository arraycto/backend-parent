package com.hyf.backend.coupon.template.dataobject;

import com.hyf.backend.coupon.template.constant.CouponDiscountCategoryEnum;
import com.hyf.backend.coupon.template.constant.CouponTemplateExpirationEnum;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponTemplateDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CouponTemplateDOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> discountCategoryCriteria;

        protected List<Criterion> expirationCodeCriteria;

        protected List<Criterion> goodsTypeLimitationCriteria;

        protected List<Criterion> weightCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            discountCategoryCriteria = new ArrayList<Criterion>();
            expirationCodeCriteria = new ArrayList<Criterion>();
            goodsTypeLimitationCriteria = new ArrayList<Criterion>();
            weightCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getDiscountCategoryCriteria() {
            return discountCategoryCriteria;
        }

        protected void addDiscountCategoryCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            discountCategoryCriteria.add(new Criterion(condition, value, "com.hyf.backend.coupon.template.typehandler.CouponDiscountCategoryEnumHandler"));
            allCriteria = null;
        }

        protected void addDiscountCategoryCriterion(String condition, CouponDiscountCategoryEnum value1, CouponDiscountCategoryEnum value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            discountCategoryCriteria.add(new Criterion(condition, value1, value2, "com.hyf.backend.coupon.template.typehandler.CouponDiscountCategoryEnumHandler"));
            allCriteria = null;
        }

        public List<Criterion> getExpirationCodeCriteria() {
            return expirationCodeCriteria;
        }

        protected void addExpirationCodeCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            expirationCodeCriteria.add(new Criterion(condition, value, "com.hyf.backend.coupon.template.typehandler.CouponExpirationEnumHandler"));
            allCriteria = null;
        }

        protected void addExpirationCodeCriterion(String condition, CouponTemplateExpirationEnum value1, CouponTemplateExpirationEnum value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            expirationCodeCriteria.add(new Criterion(condition, value1, value2, "com.hyf.backend.coupon.template.typehandler.CouponExpirationEnumHandler"));
            allCriteria = null;
        }

        public List<Criterion> getGoodsTypeLimitationCriteria() {
            return goodsTypeLimitationCriteria;
        }

        protected void addGoodsTypeLimitationCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            goodsTypeLimitationCriteria.add(new Criterion(condition, value, "com.hyf.backend.common.mybatis.typehandler.JsonListIntegerTypeHandler"));
            allCriteria = null;
        }

        protected void addGoodsTypeLimitationCriterion(String condition, List value1, List value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            goodsTypeLimitationCriteria.add(new Criterion(condition, value1, value2, "com.hyf.backend.common.mybatis.typehandler.JsonListIntegerTypeHandler"));
            allCriteria = null;
        }

        public List<Criterion> getWeightCriteria() {
            return weightCriteria;
        }

        protected void addWeightCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            weightCriteria.add(new Criterion(condition, value, "com.hyf.backend.common.mybatis.typehandler.JsonListIntegerTypeHandler"));
            allCriteria = null;
        }

        protected void addWeightCriterion(String condition, List value1, List value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            weightCriteria.add(new Criterion(condition, value1, value2, "com.hyf.backend.common.mybatis.typehandler.JsonListIntegerTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || discountCategoryCriteria.size() > 0
                || expirationCodeCriteria.size() > 0
                || goodsTypeLimitationCriteria.size() > 0
                || weightCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(discountCategoryCriteria);
                allCriteria.addAll(expirationCodeCriteria);
                allCriteria.addAll(goodsTypeLimitationCriteria);
                allCriteria.addAll(weightCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
            allCriteria = null;
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIsAvailableIsNull() {
            addCriterion("is_available is null");
            return (Criteria) this;
        }

        public Criteria andIsAvailableIsNotNull() {
            addCriterion("is_available is not null");
            return (Criteria) this;
        }

        public Criteria andIsAvailableEqualTo(Boolean value) {
            addCriterion("is_available =", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableNotEqualTo(Boolean value) {
            addCriterion("is_available <>", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableGreaterThan(Boolean value) {
            addCriterion("is_available >", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_available >=", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableLessThan(Boolean value) {
            addCriterion("is_available <", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableLessThanOrEqualTo(Boolean value) {
            addCriterion("is_available <=", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableIn(List<Boolean> values) {
            addCriterion("is_available in", values, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableNotIn(List<Boolean> values) {
            addCriterion("is_available not in", values, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableBetween(Boolean value1, Boolean value2) {
            addCriterion("is_available between", value1, value2, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_available not between", value1, value2, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsExpiredIsNull() {
            addCriterion("is_expired is null");
            return (Criteria) this;
        }

        public Criteria andIsExpiredIsNotNull() {
            addCriterion("is_expired is not null");
            return (Criteria) this;
        }

        public Criteria andIsExpiredEqualTo(Boolean value) {
            addCriterion("is_expired =", value, "isExpired");
            return (Criteria) this;
        }

        public Criteria andIsExpiredNotEqualTo(Boolean value) {
            addCriterion("is_expired <>", value, "isExpired");
            return (Criteria) this;
        }

        public Criteria andIsExpiredGreaterThan(Boolean value) {
            addCriterion("is_expired >", value, "isExpired");
            return (Criteria) this;
        }

        public Criteria andIsExpiredGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_expired >=", value, "isExpired");
            return (Criteria) this;
        }

        public Criteria andIsExpiredLessThan(Boolean value) {
            addCriterion("is_expired <", value, "isExpired");
            return (Criteria) this;
        }

        public Criteria andIsExpiredLessThanOrEqualTo(Boolean value) {
            addCriterion("is_expired <=", value, "isExpired");
            return (Criteria) this;
        }

        public Criteria andIsExpiredIn(List<Boolean> values) {
            addCriterion("is_expired in", values, "isExpired");
            return (Criteria) this;
        }

        public Criteria andIsExpiredNotIn(List<Boolean> values) {
            addCriterion("is_expired not in", values, "isExpired");
            return (Criteria) this;
        }

        public Criteria andIsExpiredBetween(Boolean value1, Boolean value2) {
            addCriterion("is_expired between", value1, value2, "isExpired");
            return (Criteria) this;
        }

        public Criteria andIsExpiredNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_expired not between", value1, value2, "isExpired");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andLogoIsNull() {
            addCriterion("logo is null");
            return (Criteria) this;
        }

        public Criteria andLogoIsNotNull() {
            addCriterion("logo is not null");
            return (Criteria) this;
        }

        public Criteria andLogoEqualTo(String value) {
            addCriterion("logo =", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotEqualTo(String value) {
            addCriterion("logo <>", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThan(String value) {
            addCriterion("logo >", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThanOrEqualTo(String value) {
            addCriterion("logo >=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThan(String value) {
            addCriterion("logo <", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThanOrEqualTo(String value) {
            addCriterion("logo <=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLike(String value) {
            addCriterion("logo like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotLike(String value) {
            addCriterion("logo not like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoIn(List<String> values) {
            addCriterion("logo in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotIn(List<String> values) {
            addCriterion("logo not in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoBetween(String value1, String value2) {
            addCriterion("logo between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotBetween(String value1, String value2) {
            addCriterion("logo not between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andDiscountCategoryIsNull() {
            addCriterion("discount_category is null");
            return (Criteria) this;
        }

        public Criteria andDiscountCategoryIsNotNull() {
            addCriterion("discount_category is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountCategoryEqualTo(CouponDiscountCategoryEnum value) {
            addDiscountCategoryCriterion("discount_category =", value, "discountCategory");
            return (Criteria) this;
        }

        public Criteria andDiscountCategoryNotEqualTo(CouponDiscountCategoryEnum value) {
            addDiscountCategoryCriterion("discount_category <>", value, "discountCategory");
            return (Criteria) this;
        }

        public Criteria andDiscountCategoryGreaterThan(CouponDiscountCategoryEnum value) {
            addDiscountCategoryCriterion("discount_category >", value, "discountCategory");
            return (Criteria) this;
        }

        public Criteria andDiscountCategoryGreaterThanOrEqualTo(CouponDiscountCategoryEnum value) {
            addDiscountCategoryCriterion("discount_category >=", value, "discountCategory");
            return (Criteria) this;
        }

        public Criteria andDiscountCategoryLessThan(CouponDiscountCategoryEnum value) {
            addDiscountCategoryCriterion("discount_category <", value, "discountCategory");
            return (Criteria) this;
        }

        public Criteria andDiscountCategoryLessThanOrEqualTo(CouponDiscountCategoryEnum value) {
            addDiscountCategoryCriterion("discount_category <=", value, "discountCategory");
            return (Criteria) this;
        }

        public Criteria andDiscountCategoryIn(List<CouponDiscountCategoryEnum> values) {
            addDiscountCategoryCriterion("discount_category in", values, "discountCategory");
            return (Criteria) this;
        }

        public Criteria andDiscountCategoryNotIn(List<CouponDiscountCategoryEnum> values) {
            addDiscountCategoryCriterion("discount_category not in", values, "discountCategory");
            return (Criteria) this;
        }

        public Criteria andDiscountCategoryBetween(CouponDiscountCategoryEnum value1, CouponDiscountCategoryEnum value2) {
            addDiscountCategoryCriterion("discount_category between", value1, value2, "discountCategory");
            return (Criteria) this;
        }

        public Criteria andDiscountCategoryNotBetween(CouponDiscountCategoryEnum value1, CouponDiscountCategoryEnum value2) {
            addDiscountCategoryCriterion("discount_category not between", value1, value2, "discountCategory");
            return (Criteria) this;
        }

        public Criteria andCouponCountIsNull() {
            addCriterion("coupon_count is null");
            return (Criteria) this;
        }

        public Criteria andCouponCountIsNotNull() {
            addCriterion("coupon_count is not null");
            return (Criteria) this;
        }

        public Criteria andCouponCountEqualTo(Integer value) {
            addCriterion("coupon_count =", value, "couponCount");
            return (Criteria) this;
        }

        public Criteria andCouponCountNotEqualTo(Integer value) {
            addCriterion("coupon_count <>", value, "couponCount");
            return (Criteria) this;
        }

        public Criteria andCouponCountGreaterThan(Integer value) {
            addCriterion("coupon_count >", value, "couponCount");
            return (Criteria) this;
        }

        public Criteria andCouponCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("coupon_count >=", value, "couponCount");
            return (Criteria) this;
        }

        public Criteria andCouponCountLessThan(Integer value) {
            addCriterion("coupon_count <", value, "couponCount");
            return (Criteria) this;
        }

        public Criteria andCouponCountLessThanOrEqualTo(Integer value) {
            addCriterion("coupon_count <=", value, "couponCount");
            return (Criteria) this;
        }

        public Criteria andCouponCountIn(List<Integer> values) {
            addCriterion("coupon_count in", values, "couponCount");
            return (Criteria) this;
        }

        public Criteria andCouponCountNotIn(List<Integer> values) {
            addCriterion("coupon_count not in", values, "couponCount");
            return (Criteria) this;
        }

        public Criteria andCouponCountBetween(Integer value1, Integer value2) {
            addCriterion("coupon_count between", value1, value2, "couponCount");
            return (Criteria) this;
        }

        public Criteria andCouponCountNotBetween(Integer value1, Integer value2) {
            addCriterion("coupon_count not between", value1, value2, "couponCount");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyIsNull() {
            addCriterion("template_key is null");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyIsNotNull() {
            addCriterion("template_key is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyEqualTo(String value) {
            addCriterion("template_key =", value, "templateKey");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyNotEqualTo(String value) {
            addCriterion("template_key <>", value, "templateKey");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyGreaterThan(String value) {
            addCriterion("template_key >", value, "templateKey");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyGreaterThanOrEqualTo(String value) {
            addCriterion("template_key >=", value, "templateKey");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyLessThan(String value) {
            addCriterion("template_key <", value, "templateKey");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyLessThanOrEqualTo(String value) {
            addCriterion("template_key <=", value, "templateKey");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyLike(String value) {
            addCriterion("template_key like", value, "templateKey");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyNotLike(String value) {
            addCriterion("template_key not like", value, "templateKey");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyIn(List<String> values) {
            addCriterion("template_key in", values, "templateKey");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyNotIn(List<String> values) {
            addCriterion("template_key not in", values, "templateKey");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyBetween(String value1, String value2) {
            addCriterion("template_key between", value1, value2, "templateKey");
            return (Criteria) this;
        }

        public Criteria andTemplateKeyNotBetween(String value1, String value2) {
            addCriterion("template_key not between", value1, value2, "templateKey");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersIsNull() {
            addCriterion("dispatch_users is null");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersIsNotNull() {
            addCriterion("dispatch_users is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersEqualTo(String value) {
            addCriterion("dispatch_users =", value, "dispatchUsers");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersNotEqualTo(String value) {
            addCriterion("dispatch_users <>", value, "dispatchUsers");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersGreaterThan(String value) {
            addCriterion("dispatch_users >", value, "dispatchUsers");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersGreaterThanOrEqualTo(String value) {
            addCriterion("dispatch_users >=", value, "dispatchUsers");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersLessThan(String value) {
            addCriterion("dispatch_users <", value, "dispatchUsers");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersLessThanOrEqualTo(String value) {
            addCriterion("dispatch_users <=", value, "dispatchUsers");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersLike(String value) {
            addCriterion("dispatch_users like", value, "dispatchUsers");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersNotLike(String value) {
            addCriterion("dispatch_users not like", value, "dispatchUsers");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersIn(List<String> values) {
            addCriterion("dispatch_users in", values, "dispatchUsers");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersNotIn(List<String> values) {
            addCriterion("dispatch_users not in", values, "dispatchUsers");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersBetween(String value1, String value2) {
            addCriterion("dispatch_users between", value1, value2, "dispatchUsers");
            return (Criteria) this;
        }

        public Criteria andDispatchUsersNotBetween(String value1, String value2) {
            addCriterion("dispatch_users not between", value1, value2, "dispatchUsers");
            return (Criteria) this;
        }

        public Criteria andExpirationCodeIsNull() {
            addCriterion("expiration_code is null");
            return (Criteria) this;
        }

        public Criteria andExpirationCodeIsNotNull() {
            addCriterion("expiration_code is not null");
            return (Criteria) this;
        }

        public Criteria andExpirationCodeEqualTo(CouponTemplateExpirationEnum value) {
            addExpirationCodeCriterion("expiration_code =", value, "expirationCode");
            return (Criteria) this;
        }

        public Criteria andExpirationCodeNotEqualTo(CouponTemplateExpirationEnum value) {
            addExpirationCodeCriterion("expiration_code <>", value, "expirationCode");
            return (Criteria) this;
        }

        public Criteria andExpirationCodeGreaterThan(CouponTemplateExpirationEnum value) {
            addExpirationCodeCriterion("expiration_code >", value, "expirationCode");
            return (Criteria) this;
        }

        public Criteria andExpirationCodeGreaterThanOrEqualTo(CouponTemplateExpirationEnum value) {
            addExpirationCodeCriterion("expiration_code >=", value, "expirationCode");
            return (Criteria) this;
        }

        public Criteria andExpirationCodeLessThan(CouponTemplateExpirationEnum value) {
            addExpirationCodeCriterion("expiration_code <", value, "expirationCode");
            return (Criteria) this;
        }

        public Criteria andExpirationCodeLessThanOrEqualTo(CouponTemplateExpirationEnum value) {
            addExpirationCodeCriterion("expiration_code <=", value, "expirationCode");
            return (Criteria) this;
        }

        public Criteria andExpirationCodeIn(List<CouponTemplateExpirationEnum> values) {
            addExpirationCodeCriterion("expiration_code in", values, "expirationCode");
            return (Criteria) this;
        }

        public Criteria andExpirationCodeNotIn(List<CouponTemplateExpirationEnum> values) {
            addExpirationCodeCriterion("expiration_code not in", values, "expirationCode");
            return (Criteria) this;
        }

        public Criteria andExpirationCodeBetween(CouponTemplateExpirationEnum value1, CouponTemplateExpirationEnum value2) {
            addExpirationCodeCriterion("expiration_code between", value1, value2, "expirationCode");
            return (Criteria) this;
        }

        public Criteria andExpirationCodeNotBetween(CouponTemplateExpirationEnum value1, CouponTemplateExpirationEnum value2) {
            addExpirationCodeCriterion("expiration_code not between", value1, value2, "expirationCode");
            return (Criteria) this;
        }

        public Criteria andExpirationGapIsNull() {
            addCriterion("expiration_gap is null");
            return (Criteria) this;
        }

        public Criteria andExpirationGapIsNotNull() {
            addCriterion("expiration_gap is not null");
            return (Criteria) this;
        }

        public Criteria andExpirationGapEqualTo(Integer value) {
            addCriterion("expiration_gap =", value, "expirationGap");
            return (Criteria) this;
        }

        public Criteria andExpirationGapNotEqualTo(Integer value) {
            addCriterion("expiration_gap <>", value, "expirationGap");
            return (Criteria) this;
        }

        public Criteria andExpirationGapGreaterThan(Integer value) {
            addCriterion("expiration_gap >", value, "expirationGap");
            return (Criteria) this;
        }

        public Criteria andExpirationGapGreaterThanOrEqualTo(Integer value) {
            addCriterion("expiration_gap >=", value, "expirationGap");
            return (Criteria) this;
        }

        public Criteria andExpirationGapLessThan(Integer value) {
            addCriterion("expiration_gap <", value, "expirationGap");
            return (Criteria) this;
        }

        public Criteria andExpirationGapLessThanOrEqualTo(Integer value) {
            addCriterion("expiration_gap <=", value, "expirationGap");
            return (Criteria) this;
        }

        public Criteria andExpirationGapIn(List<Integer> values) {
            addCriterion("expiration_gap in", values, "expirationGap");
            return (Criteria) this;
        }

        public Criteria andExpirationGapNotIn(List<Integer> values) {
            addCriterion("expiration_gap not in", values, "expirationGap");
            return (Criteria) this;
        }

        public Criteria andExpirationGapBetween(Integer value1, Integer value2) {
            addCriterion("expiration_gap between", value1, value2, "expirationGap");
            return (Criteria) this;
        }

        public Criteria andExpirationGapNotBetween(Integer value1, Integer value2) {
            addCriterion("expiration_gap not between", value1, value2, "expirationGap");
            return (Criteria) this;
        }

        public Criteria andExpirationDeadlineIsNull() {
            addCriterion("expiration_deadline is null");
            return (Criteria) this;
        }

        public Criteria andExpirationDeadlineIsNotNull() {
            addCriterion("expiration_deadline is not null");
            return (Criteria) this;
        }

        public Criteria andExpirationDeadlineEqualTo(Date value) {
            addCriterion("expiration_deadline =", value, "expirationDeadline");
            return (Criteria) this;
        }

        public Criteria andExpirationDeadlineNotEqualTo(Date value) {
            addCriterion("expiration_deadline <>", value, "expirationDeadline");
            return (Criteria) this;
        }

        public Criteria andExpirationDeadlineGreaterThan(Date value) {
            addCriterion("expiration_deadline >", value, "expirationDeadline");
            return (Criteria) this;
        }

        public Criteria andExpirationDeadlineGreaterThanOrEqualTo(Date value) {
            addCriterion("expiration_deadline >=", value, "expirationDeadline");
            return (Criteria) this;
        }

        public Criteria andExpirationDeadlineLessThan(Date value) {
            addCriterion("expiration_deadline <", value, "expirationDeadline");
            return (Criteria) this;
        }

        public Criteria andExpirationDeadlineLessThanOrEqualTo(Date value) {
            addCriterion("expiration_deadline <=", value, "expirationDeadline");
            return (Criteria) this;
        }

        public Criteria andExpirationDeadlineIn(List<Date> values) {
            addCriterion("expiration_deadline in", values, "expirationDeadline");
            return (Criteria) this;
        }

        public Criteria andExpirationDeadlineNotIn(List<Date> values) {
            addCriterion("expiration_deadline not in", values, "expirationDeadline");
            return (Criteria) this;
        }

        public Criteria andExpirationDeadlineBetween(Date value1, Date value2) {
            addCriterion("expiration_deadline between", value1, value2, "expirationDeadline");
            return (Criteria) this;
        }

        public Criteria andExpirationDeadlineNotBetween(Date value1, Date value2) {
            addCriterion("expiration_deadline not between", value1, value2, "expirationDeadline");
            return (Criteria) this;
        }

        public Criteria andManjianQuotaIsNull() {
            addCriterion("manjian_quota is null");
            return (Criteria) this;
        }

        public Criteria andManjianQuotaIsNotNull() {
            addCriterion("manjian_quota is not null");
            return (Criteria) this;
        }

        public Criteria andManjianQuotaEqualTo(Integer value) {
            addCriterion("manjian_quota =", value, "manjianQuota");
            return (Criteria) this;
        }

        public Criteria andManjianQuotaNotEqualTo(Integer value) {
            addCriterion("manjian_quota <>", value, "manjianQuota");
            return (Criteria) this;
        }

        public Criteria andManjianQuotaGreaterThan(Integer value) {
            addCriterion("manjian_quota >", value, "manjianQuota");
            return (Criteria) this;
        }

        public Criteria andManjianQuotaGreaterThanOrEqualTo(Integer value) {
            addCriterion("manjian_quota >=", value, "manjianQuota");
            return (Criteria) this;
        }

        public Criteria andManjianQuotaLessThan(Integer value) {
            addCriterion("manjian_quota <", value, "manjianQuota");
            return (Criteria) this;
        }

        public Criteria andManjianQuotaLessThanOrEqualTo(Integer value) {
            addCriterion("manjian_quota <=", value, "manjianQuota");
            return (Criteria) this;
        }

        public Criteria andManjianQuotaIn(List<Integer> values) {
            addCriterion("manjian_quota in", values, "manjianQuota");
            return (Criteria) this;
        }

        public Criteria andManjianQuotaNotIn(List<Integer> values) {
            addCriterion("manjian_quota not in", values, "manjianQuota");
            return (Criteria) this;
        }

        public Criteria andManjianQuotaBetween(Integer value1, Integer value2) {
            addCriterion("manjian_quota between", value1, value2, "manjianQuota");
            return (Criteria) this;
        }

        public Criteria andManjianQuotaNotBetween(Integer value1, Integer value2) {
            addCriterion("manjian_quota not between", value1, value2, "manjianQuota");
            return (Criteria) this;
        }

        public Criteria andLijianQuotaIsNull() {
            addCriterion("lijian_quota is null");
            return (Criteria) this;
        }

        public Criteria andLijianQuotaIsNotNull() {
            addCriterion("lijian_quota is not null");
            return (Criteria) this;
        }

        public Criteria andLijianQuotaEqualTo(Integer value) {
            addCriterion("lijian_quota =", value, "lijianQuota");
            return (Criteria) this;
        }

        public Criteria andLijianQuotaNotEqualTo(Integer value) {
            addCriterion("lijian_quota <>", value, "lijianQuota");
            return (Criteria) this;
        }

        public Criteria andLijianQuotaGreaterThan(Integer value) {
            addCriterion("lijian_quota >", value, "lijianQuota");
            return (Criteria) this;
        }

        public Criteria andLijianQuotaGreaterThanOrEqualTo(Integer value) {
            addCriterion("lijian_quota >=", value, "lijianQuota");
            return (Criteria) this;
        }

        public Criteria andLijianQuotaLessThan(Integer value) {
            addCriterion("lijian_quota <", value, "lijianQuota");
            return (Criteria) this;
        }

        public Criteria andLijianQuotaLessThanOrEqualTo(Integer value) {
            addCriterion("lijian_quota <=", value, "lijianQuota");
            return (Criteria) this;
        }

        public Criteria andLijianQuotaIn(List<Integer> values) {
            addCriterion("lijian_quota in", values, "lijianQuota");
            return (Criteria) this;
        }

        public Criteria andLijianQuotaNotIn(List<Integer> values) {
            addCriterion("lijian_quota not in", values, "lijianQuota");
            return (Criteria) this;
        }

        public Criteria andLijianQuotaBetween(Integer value1, Integer value2) {
            addCriterion("lijian_quota between", value1, value2, "lijianQuota");
            return (Criteria) this;
        }

        public Criteria andLijianQuotaNotBetween(Integer value1, Integer value2) {
            addCriterion("lijian_quota not between", value1, value2, "lijianQuota");
            return (Criteria) this;
        }

        public Criteria andZhekouQuotaIsNull() {
            addCriterion("zhekou_quota is null");
            return (Criteria) this;
        }

        public Criteria andZhekouQuotaIsNotNull() {
            addCriterion("zhekou_quota is not null");
            return (Criteria) this;
        }

        public Criteria andZhekouQuotaEqualTo(Integer value) {
            addCriterion("zhekou_quota =", value, "zhekouQuota");
            return (Criteria) this;
        }

        public Criteria andZhekouQuotaNotEqualTo(Integer value) {
            addCriterion("zhekou_quota <>", value, "zhekouQuota");
            return (Criteria) this;
        }

        public Criteria andZhekouQuotaGreaterThan(Integer value) {
            addCriterion("zhekou_quota >", value, "zhekouQuota");
            return (Criteria) this;
        }

        public Criteria andZhekouQuotaGreaterThanOrEqualTo(Integer value) {
            addCriterion("zhekou_quota >=", value, "zhekouQuota");
            return (Criteria) this;
        }

        public Criteria andZhekouQuotaLessThan(Integer value) {
            addCriterion("zhekou_quota <", value, "zhekouQuota");
            return (Criteria) this;
        }

        public Criteria andZhekouQuotaLessThanOrEqualTo(Integer value) {
            addCriterion("zhekou_quota <=", value, "zhekouQuota");
            return (Criteria) this;
        }

        public Criteria andZhekouQuotaIn(List<Integer> values) {
            addCriterion("zhekou_quota in", values, "zhekouQuota");
            return (Criteria) this;
        }

        public Criteria andZhekouQuotaNotIn(List<Integer> values) {
            addCriterion("zhekou_quota not in", values, "zhekouQuota");
            return (Criteria) this;
        }

        public Criteria andZhekouQuotaBetween(Integer value1, Integer value2) {
            addCriterion("zhekou_quota between", value1, value2, "zhekouQuota");
            return (Criteria) this;
        }

        public Criteria andZhekouQuotaNotBetween(Integer value1, Integer value2) {
            addCriterion("zhekou_quota not between", value1, value2, "zhekouQuota");
            return (Criteria) this;
        }

        public Criteria andDiscountBaseIsNull() {
            addCriterion("discount_base is null");
            return (Criteria) this;
        }

        public Criteria andDiscountBaseIsNotNull() {
            addCriterion("discount_base is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountBaseEqualTo(Integer value) {
            addCriterion("discount_base =", value, "discountBase");
            return (Criteria) this;
        }

        public Criteria andDiscountBaseNotEqualTo(Integer value) {
            addCriterion("discount_base <>", value, "discountBase");
            return (Criteria) this;
        }

        public Criteria andDiscountBaseGreaterThan(Integer value) {
            addCriterion("discount_base >", value, "discountBase");
            return (Criteria) this;
        }

        public Criteria andDiscountBaseGreaterThanOrEqualTo(Integer value) {
            addCriterion("discount_base >=", value, "discountBase");
            return (Criteria) this;
        }

        public Criteria andDiscountBaseLessThan(Integer value) {
            addCriterion("discount_base <", value, "discountBase");
            return (Criteria) this;
        }

        public Criteria andDiscountBaseLessThanOrEqualTo(Integer value) {
            addCriterion("discount_base <=", value, "discountBase");
            return (Criteria) this;
        }

        public Criteria andDiscountBaseIn(List<Integer> values) {
            addCriterion("discount_base in", values, "discountBase");
            return (Criteria) this;
        }

        public Criteria andDiscountBaseNotIn(List<Integer> values) {
            addCriterion("discount_base not in", values, "discountBase");
            return (Criteria) this;
        }

        public Criteria andDiscountBaseBetween(Integer value1, Integer value2) {
            addCriterion("discount_base between", value1, value2, "discountBase");
            return (Criteria) this;
        }

        public Criteria andDiscountBaseNotBetween(Integer value1, Integer value2) {
            addCriterion("discount_base not between", value1, value2, "discountBase");
            return (Criteria) this;
        }

        public Criteria andEachGetLimitationIsNull() {
            addCriterion("each_get_limitation is null");
            return (Criteria) this;
        }

        public Criteria andEachGetLimitationIsNotNull() {
            addCriterion("each_get_limitation is not null");
            return (Criteria) this;
        }

        public Criteria andEachGetLimitationEqualTo(Integer value) {
            addCriterion("each_get_limitation =", value, "eachGetLimitation");
            return (Criteria) this;
        }

        public Criteria andEachGetLimitationNotEqualTo(Integer value) {
            addCriterion("each_get_limitation <>", value, "eachGetLimitation");
            return (Criteria) this;
        }

        public Criteria andEachGetLimitationGreaterThan(Integer value) {
            addCriterion("each_get_limitation >", value, "eachGetLimitation");
            return (Criteria) this;
        }

        public Criteria andEachGetLimitationGreaterThanOrEqualTo(Integer value) {
            addCriterion("each_get_limitation >=", value, "eachGetLimitation");
            return (Criteria) this;
        }

        public Criteria andEachGetLimitationLessThan(Integer value) {
            addCriterion("each_get_limitation <", value, "eachGetLimitation");
            return (Criteria) this;
        }

        public Criteria andEachGetLimitationLessThanOrEqualTo(Integer value) {
            addCriterion("each_get_limitation <=", value, "eachGetLimitation");
            return (Criteria) this;
        }

        public Criteria andEachGetLimitationIn(List<Integer> values) {
            addCriterion("each_get_limitation in", values, "eachGetLimitation");
            return (Criteria) this;
        }

        public Criteria andEachGetLimitationNotIn(List<Integer> values) {
            addCriterion("each_get_limitation not in", values, "eachGetLimitation");
            return (Criteria) this;
        }

        public Criteria andEachGetLimitationBetween(Integer value1, Integer value2) {
            addCriterion("each_get_limitation between", value1, value2, "eachGetLimitation");
            return (Criteria) this;
        }

        public Criteria andEachGetLimitationNotBetween(Integer value1, Integer value2) {
            addCriterion("each_get_limitation not between", value1, value2, "eachGetLimitation");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationIsNull() {
            addCriterion("goods_type_limitation is null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationIsNotNull() {
            addCriterion("goods_type_limitation is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationEqualTo(List value) {
            addGoodsTypeLimitationCriterion("goods_type_limitation =", value, "goodsTypeLimitation");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationNotEqualTo(List value) {
            addGoodsTypeLimitationCriterion("goods_type_limitation <>", value, "goodsTypeLimitation");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationGreaterThan(List value) {
            addGoodsTypeLimitationCriterion("goods_type_limitation >", value, "goodsTypeLimitation");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationGreaterThanOrEqualTo(List value) {
            addGoodsTypeLimitationCriterion("goods_type_limitation >=", value, "goodsTypeLimitation");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationLessThan(List value) {
            addGoodsTypeLimitationCriterion("goods_type_limitation <", value, "goodsTypeLimitation");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationLessThanOrEqualTo(List value) {
            addGoodsTypeLimitationCriterion("goods_type_limitation <=", value, "goodsTypeLimitation");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationLike(List value) {
            addGoodsTypeLimitationCriterion("goods_type_limitation like", value, "goodsTypeLimitation");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationNotLike(List value) {
            addGoodsTypeLimitationCriterion("goods_type_limitation not like", value, "goodsTypeLimitation");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationIn(List<List> values) {
            addGoodsTypeLimitationCriterion("goods_type_limitation in", values, "goodsTypeLimitation");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationNotIn(List<List> values) {
            addGoodsTypeLimitationCriterion("goods_type_limitation not in", values, "goodsTypeLimitation");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationBetween(List value1, List value2) {
            addGoodsTypeLimitationCriterion("goods_type_limitation between", value1, value2, "goodsTypeLimitation");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLimitationNotBetween(List value1, List value2) {
            addGoodsTypeLimitationCriterion("goods_type_limitation not between", value1, value2, "goodsTypeLimitation");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(List value) {
            addWeightCriterion("weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(List value) {
            addWeightCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(List value) {
            addWeightCriterion("weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(List value) {
            addWeightCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(List value) {
            addWeightCriterion("weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(List value) {
            addWeightCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLike(List value) {
            addWeightCriterion("weight like", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotLike(List value) {
            addWeightCriterion("weight not like", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<List> values) {
            addWeightCriterion("weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<List> values) {
            addWeightCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(List value1, List value2) {
            addWeightCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(List value1, List value2) {
            addWeightCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andTagIsNull() {
            addCriterion("tag is null");
            return (Criteria) this;
        }

        public Criteria andTagIsNotNull() {
            addCriterion("tag is not null");
            return (Criteria) this;
        }

        public Criteria andTagEqualTo(String value) {
            addCriterion("tag =", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotEqualTo(String value) {
            addCriterion("tag <>", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThan(String value) {
            addCriterion("tag >", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThanOrEqualTo(String value) {
            addCriterion("tag >=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThan(String value) {
            addCriterion("tag <", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThanOrEqualTo(String value) {
            addCriterion("tag <=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLike(String value) {
            addCriterion("tag like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotLike(String value) {
            addCriterion("tag not like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagIn(List<String> values) {
            addCriterion("tag in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotIn(List<String> values) {
            addCriterion("tag not in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagBetween(String value1, String value2) {
            addCriterion("tag between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotBetween(String value1, String value2) {
            addCriterion("tag not between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andDescIsNull() {
            addCriterion("`desc` is null");
            return (Criteria) this;
        }

        public Criteria andDescIsNotNull() {
            addCriterion("`desc` is not null");
            return (Criteria) this;
        }

        public Criteria andDescEqualTo(String value) {
            addCriterion("`desc` =", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotEqualTo(String value) {
            addCriterion("`desc` <>", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThan(String value) {
            addCriterion("`desc` >", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanOrEqualTo(String value) {
            addCriterion("`desc` >=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThan(String value) {
            addCriterion("`desc` <", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThanOrEqualTo(String value) {
            addCriterion("`desc` <=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLike(String value) {
            addCriterion("`desc` like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotLike(String value) {
            addCriterion("`desc` not like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescIn(List<String> values) {
            addCriterion("`desc` in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotIn(List<String> values) {
            addCriterion("`desc` not in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescBetween(String value1, String value2) {
            addCriterion("`desc` between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotBetween(String value1, String value2) {
            addCriterion("`desc` not between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(Integer value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Integer value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Integer value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Integer value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Integer value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Integer> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Integer> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Integer value1, Integer value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(Integer value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(Integer value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(Integer value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(Integer value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(Integer value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<Integer> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<Integer> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(Integer value1, Integer value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}