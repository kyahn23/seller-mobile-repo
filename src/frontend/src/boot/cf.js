import axios from "axios";

let g_cxt = "/";
g_cxt = g_cxt.substring(0, g_cxt.length - 1);

/** 필요 함수: cf_whatIsIt, cf_isEmpty */
/**
 * 해당객체의 타입명을 반환해주는 함수
 * @param obj
 * @returns
 */
function cf_whatIsIt(obj) {
  let stringConstructor = "test".constructor;
  let numberConstructor = Number("123").constructor;
  let dateConstructor = new Date().constructor;
  let arrayConstructor = [].constructor;
  let objectConstructor = {}.constructor;

  if (obj === null) {
    return "null";
  } else if (obj === undefined) {
    return "undefined";
  } else if (obj.constructor === stringConstructor) {
    return "string";
  } else if (obj.constructor === numberConstructor) {
    if (isNaN(obj)) return "nothing";
    return "number";
  } else if (obj.constructor === dateConstructor) {
    return "date";
  } else if (obj.constructor === arrayConstructor) {
    return "array";
  } else if (obj.constructor === objectConstructor) {
    return "object";
  } else {
    return "nothing";
  }
}

/**
 * 해당 파라메타가 비어있는지 확인
 *
 * @param obj
 * @returns
 */
function cf_isEmpty(obj) {
  let objtyp = cf_whatIsIt(obj);
  if (objtyp == "null") return true;
  else if (objtyp == "undefined") return true;
  else if (objtyp == "string" && obj == "") return true;
  else if (objtyp == "object" && obj == {}) return true;
  else if (objtyp == "object" && obj.length == undefined) return true;
  return false;
}

const rsltFailArr = ["error", "dev-error", "FAIL"];

export default async ({ Vue, store }) => {
  const cf = {
    /**
     * ajax 통신함수
     *
     * @param url
     * @param param
     * @param callback
     * @param loadingbar
     * @param options
     * @returns
     */
    async call(url, param, callback, loadingbar, options) {
      if (cf_isEmpty(options)) options = {};

      if (loadingbar !== false) {
        store.commit("setLoading", { isLoading: true });
      }

      axios
        .post(g_cxt + url, param, options)
        .then(function(response) {
          store.commit("setLoading", { isLoading: false });
          if (rsltFailArr.includes(response.data.rsltStat)) {
            if (
              response.data.rsltStat == "dev-error" &&
              !cf_isEmpty(response.data.errMsg)
            ) {
              store.commit("setNotification", {
                color: "negative",
                textColor: "white",
                message: response.data.errMsg,
                caption: ""
              });
            } else {
              store.commit("setNotification", {
                color: "negative",
                textColor: "white",
                message: "처리 중 오류가 발생했습니다.",
                caption: "관리자에게 문의하세요."
              });
            }
          } else {
            if (callback != null) {
              if (
                cf_whatIsIt(response.data) === "string" &&
                response.data.indexOf("<!DOCTYPE html>") != -1
              ) {
                store.commit("setNotification", {
                  color: "negative",
                  textColor: "white",
                  message: "처리 중 오류가 발생했습니다.",
                  caption: "관리자에게 문의하세요."
                });
              } else {
                callback(response.data);
              }
            }
          }
        })
        .catch(function(error) {
          store.commit("setLoading", { isLoading: false });
          if (error.message == "Network Error") {
            store.commit("setNotification", {
              color: "negative",
              textColor: "white",
              message: "네트워크 상태 또는 서버 구동상태를 확인해 주세요.",
              caption: ""
            });
          } else {
            store.commit("setNotification", {
              color: "negative",
              textColor: "white",
              message: "처리 중 오류가 발생했습니다.",
              caption: "관리자에게 문의하세요."
            });
          }
          console.log(error);
        });
    },

    /**
     * 해당 파라메타가 비어있는지 확인
     *
     * @param obj
     * @returns
     */
    isEmpty(obj) {
      return cf_isEmpty(obj);
    },

    /**
     * JS Date 객체를 MySQL/MariaDB DATETIME 타입으로 변환
     * @param date
     * @returns
     */
    dateToDatetime(date) {
      if (!date || cf_whatIsIt(date) !== "date") {
        return "";
      }
      return date
        .toISOString()
        .slice(0, 19)
        .replace("T", " ");
    },

    /**
     * 저장 이미지 명으로 저장 이미지 호출 함수
     * @param imageName
     * @returns
     */
    imagePath(imageName) {
      if (imageName !== undefined && imageName !== null && imageName !== "") {
        return process.env.API + "/api/common/image?filename=" + imageName;
      } else {
        return "";
      }
    }
  };

  cf.Vue = Vue;

  Vue.prototype.$cf = cf;
};
