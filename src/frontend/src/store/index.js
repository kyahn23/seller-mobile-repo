import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default function() {
  const store = new Vuex.Store({
    state: {
      isLoading: false,
      isLayer: false,
      quitDialog: false,
      notification: {
        group: false,
        color: "",
        textColor: "",
        message: "",
        caption: ""
      },
      isAuth: false,
      currentUser: "",
      mbrName: "",
      bnName:"",
      cs:{},
      mainCsSearch:{}
    },
    getters: {
      isLoading: state => state.isLoading,
      isLayer: state => state.isLayer,
      quitDialog: state => state.quitDialog,
      notification: state => state.notification,
      isAuth: state => state.isAuth,
      currentUser: state => state.currentUser,
      mbrName: state => state.mbrName,
      bnName: state => state.bnName,
      cs: state => state.cs,
      mainCsSearch: state => state.mainCsSearch
    },
    mutations: {
      setLoading(state, payload) {
        state.isLoading = payload.isLoading;
      },
      setLayer(state, payload) {
        state.isLayer = payload.isLayer;
      },
      setQuitDialog(state, payload) {
        state.quitDialog = payload.quitDialog;
      },
      setNotification(state, payload) {
        state.notification = {
          group: false,
          color: payload.color,
          textColor: payload.textColor,
          message: payload.message,
          caption: payload.caption
        };
      },
      setAuth(state, payload) {
        state.isAuth = payload.isAuth;
      },
      setCurrentUser(state, payload) {
        state.currentUser = payload.currentUser;
      },
      setMbrName(state, payload){
        state.mbrName = payload.mbrName
      },
      setBnName(state, payload){
        state.bnName = payload.bnName
      },
      setCs(state, payload){
        state.cs = payload.cs
      },
      setCsSearch(state, payload){
        state.mainCsSearch = payload.mainCsSearch
      }
    }
  });

  return store;
}
