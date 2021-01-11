export default ({ Vue }) => {
  /** 돈 양식 필터 */
  Vue.filter("showMeTheMoney", value => {
    if (!value) {
      return "없음";
    }
    return parseInt(value, 10).toLocaleString() + "원";
  });

  /** 포함/미포함 여부 필터 */
  Vue.filter("isIncluded", value => {
    if (!value) {
      return "";
    }

    value = value.substring(0, 1).toUpperCase();

    if (value === "Y" || value === "T") {
      return "포함";
    }

    return "미포함";
  });

  /** 값이 null 일때 (숫자, 소수점 1자리) */
  Vue.filter("ifNullNumber", value => {
    if (value) {
      let parsed = parseFloat(value);
      if (!isNaN(parsed)) {
        return parsed.toFixed(1);
      }
    }

    return "0.0";
  });

  /** 값이 null 일때 (문자열) */
  Vue.filter("ifNullString", value => {
    if (!value) {
      return "없음";
    }

    return value;
  });
};
