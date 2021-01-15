<template>
  <q-layout view="hHh lpr fFf">
    <q-header class="bg-primary text-white" height-hint="98">
      <q-toolbar class="q-px-none">
        <q-btn
          id="mainBackBtn"
          :icon="leftBtnIcon"
          unelevated
          color="standard"
          :text-color="leftBtnColor"
          size="md"
          @click="goBackBtn"
        />
        <q-toolbar-title
          class="text-center text-weight-bold"
          style="font-size: 1.2em;"
        >
          {{ titleLabel }}
        </q-toolbar-title>
        <q-btn
          icon="power_settings_new"
          :label="logOutLabel"
          stack
          unelevated
          color="standard"
          size="sm"
          @click="logOut"
        />
      </q-toolbar>
      <q-tabs indicator-color="white" dense align="justify">
        <q-route-tab to="/main" label="홈"/>
        <q-route-tab to="/board" label="공지사항"/>
        <q-route-tab to="/status" label="상담현황"/>
        <q-route-tab to="/product" label="상품현황"/>
      </q-tabs>
    </q-header>

    <q-page-container>
      <router-view/>
    </q-page-container>

    <q-footer id="mainFooter" class="bg-grey-8 text-white">
      <div class="q-gutter-y-md">
        <q-tabs
          indicator-color="transparent"
          class="bg-grey-1 text-black"
          dense
          align="justify"
          active-color="primary"
        >
          <q-route-tab to="/main" icon="home" label="홈"/>
          <q-route-tab to="/board" icon="list" label="공지사항"/>
          <q-route-tab to="/status" icon="contacts" label="상담현황"/>
          <q-route-tab to="/product" icon="smartphone" label="상품현황"/>
        </q-tabs>
      </div>
    </q-footer>

    <q-dialog v-model="quitDialog" persistent>
      <q-card style="width: 300px">
        <q-card-section>
          <div class="text-subtitle1 text-weight-bold">PentaPhone 종료</div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          PentaPhone을 종료하시겠습니까?
        </q-card-section>

        <q-card-actions align="evenly">
          <q-btn
            unelevated
            rounded
            color="primary"
            label="취소"
            style="width: 48%"
            @click="closeQuitDialog"
          />
          <q-btn
            unelevated
            rounded
            color="primary"
            label="종료"
            style="width: 48%"
            @click="quitConfirm"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>

    <q-inner-loading :showing="isLoading" class="z-max">
      <q-spinner color="primary" size="3em" />
    </q-inner-loading>
  </q-layout>
</template>

<script>
export default {
  name: "MainLayout",
  data() {
    return {
      isMain: true,
      isBackBtn: false,
      isLayerBackBtn: false,
      isFromLayerBackBtn: false,
      titleLabel: "펜타폰",
      logOutLabel: "로그아웃",
      dialogFrom: "/main",
      pageFrom: [],
      layerFrom: []
    };
  },
  mounted() {
  },
  watch: {
    notification(newNotification) {
      this.$q.notify(newNotification);
    },
  },
  methods: {
    /** 백 버튼 클릭 이벤트 */
    goBackBtn() {
      if (!this.isMain) {
        this.isBackBtn = true;
        this.$router.push({path: this.pageFrom.pop()});
      }
    },
    /** 로그아웃 */
    logOut() {
      this.$store.commit("setAuth", {isAuth: false});
      this.$store.commit("setCurrentUser", {currentUser: ""});
      this.$store.commit("setAuth", {isAuth: true});
      this.$store.commit("setMbrName", {mbrName: ""});
      this.$store.commit("setBnName", {bnName: ""});
      window.location.href = process.env.API + "/logout"
    },
    /** 앱 종료 취소 함수 */
    closeQuitDialog() {
      this.$store.commit("setQuitDialog", {quitDialog: false});
    },
    /** 앱 종료 확인 함수 */
    quitConfirm() {
      PentasWebView.quitApp();
    }
  },
  computed: {
    isLoading: {
      get() {
        return this.$store.getters.isLoading;
      }
    },
    isLayer: {
      get() {
        return this.$store.getters.isLayer;
      }
    },
    notification: {
      get() {
        return this.$store.getters.notification;
      }
    },
    isLogin: {
      get() {
        return this.$store.getters.isAuth;
      }
    },
    leftBtnIcon: {
      get() {
        if (
          this.isMain ||
          (this.isLayer && this.dialogFrom.includes("/main"))
        ) {
          return "check_box_outline_blank";
        }
        return "arrow_back_ios";
      }
    },
    leftBtnColor: {
      get() {
        if (
          this.isMain ||
          (this.isLayer && this.dialogFrom.includes("/main"))
        ) {
          return "primary";
        }
        return "white";
      }
    },
    quitDialog: {
      get() {
        return this.$store.getters.quitDialog;
      }
    }
  }
};
</script>
<style>
#mainFooter .q-tabs {
  min-height: 5em;
}

#mainFooter .q-tab__label {
  font-size: 0.8em;
}
</style>
