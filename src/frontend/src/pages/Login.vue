<template>
  <q-layout class="column flex-center q-pb-xl" v-cloak>
    <q-img
      class="q-pt-xl q-pb-md"
      src="images/pentas_logo_320.png"
      style="width: 60%; max-width: 240px;"
      contain
    />
    <p class="text-subtitle1 text-weight-medium">판매업체 전용 App</p>
    <q-form
      class="flex column fit"
      ref="loginForm"
      name="loginForm"
      :action="rootCtx + '/loginProc'"
      method="post"
      @submit="onSubmit"
    >
      <q-input
        v-model="userId"
        label="아이디 (이메일)"
        rounded
        outlined
        stack-label
        name="userId"
        id="userId"
        class="q-px-lg q-pt-sm q-mb-xs fit"
        :rules="[
          $rules.required('아이디를 입력해주세요.'),
          $rules.email('올바른 이메일이 아닙니다.')
        ]"
      />
      <q-input
        v-model="inputPw"
        label="비밀번호"
        rounded
        outlined
        name="inputPw"
        id="inputPw"
        stack-label
        :type="isPwd ? 'password' : 'text'"
        class="q-px-lg q-py-sm fit"
        :rules="[$rules.required('비밀번호를 입력해주세요.')]"
      >
        <template v-slot:append>
          <q-icon
            :name="isPwd ? 'visibility_off' : 'visibility'"
            class="cursor-pointer"
            @click="isPwd = !isPwd"
          />
        </template>
      </q-input>
      <input name="userPw" id="userPw" type="hidden" :value="userPw"/>
      <div class="flex column q-px-lg q-pt-lg q-gutter-y-md fit">
        <q-btn
          unelevated
          rounded
          color="primary"
          label="로그인"
          type="submit"
        />
      </div>
    </q-form>
    <q-footer class="text-center justify-center row bg-white">
      <q-tabs class="text-grey-7" align="center">
        ©2021 PENTAS. All rights reserved.
      </q-tabs>
    </q-footer>

    <q-dialog v-model="alertYn" persistent full-width>
      <q-card>
        <q-card-section>
          <div class="text-h6">{{ alertTitle }}</div>
        </q-card-section>

        <q-card-section class="q-pt-none" v-html="alertContent">
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="확인" color="primary" v-close-popup></q-btn>
        </q-card-actions>
      </q-card>
    </q-dialog>

  </q-layout>
</template>

<script>
import sha256 from "crypto-js/sha256";

export default {
  name: "Login",
  data() {
    return {
      isPwd: true,
      alertYn: false,
      alertTitle: "",
      alertContent: "",
      userId: "",
      inputPw: "",
      rootCtx: process.env.API
    };
  },
  created() {

  },
  mounted() {
    this.urlchk()
  },
  methods: {
    urlchk() {
      let currentPath = window.location.href
      let loginStat = ''
      if (currentPath.indexOf('loginStat') !== -1) {
        loginStat = currentPath.substring(currentPath.lastIndexOf('=') + 1)
        if (loginStat === 'FAIL') {
          this.$q.notify({
            color: "grey-8",
            textColor: "white",
            message: "로그인 실패 했습니다. ID 또는 비밀번호를 확인해주세요."
          })
        } else if (loginStat === 'LOGOUT') {
          this.$q.notify({
            color: "grey-8",
            textColor: "white",
            message: "로그아웃 되었습니다."
          })
        } else if (loginStat === 'BNCARD') {
          this.alertYn = true
          this.alertTitle = '사업자 등록증 미확인'
          this.alertContent = '<span>현재 사업자 등록증 확인 중입니다.</span><br><span>확인 완료 후 로그인 하실 수 있습니다.<br><p>(평일(공휴일 제외) 24시간이내 완료)</p></span>'
        } else if (loginStat === 'PWINIT') {
          this.alertYn = true
          this.alertTitle = '비밀번호 초기화'
          this.alertContent = '<span>새로운 비밀번호 설정은</span><br><span>PC를 통해서만 가능합니다.</span>'
        } else if (loginStat.indexOf("FAIL_PWERR_CNT_") !== -1) {
          let pwerrCntStr = loginStat.slice(15)
          if (pwerrCntStr === 'OVER') {
            this.$q.notify({
              color: "grey-8",
              textColor: "white",
              message: "로그인을 5회 이상 실패했습니다. PC화면에서 비밀번호를 재설정 해주세요."
            })
          } else {
            this.$q.notify({
              color: "grey-8",
              textColor: "white",
              message: "로그인을 " + pwerrCntStr + "회 실패했습니다. 5회 이상 실패 시 로그인이 제한됩니다."
            })
          }
        }
      }
    },
    onSubmit(evt) {
      if (!this.$cf.isEmpty(this.userId) && !this.$cf.isEmpty(this.inputPw)) {
        evt.target.submit();
      }
    }
  },
  computed: {
    userPw: function () {
      return sha256(this.inputPw).toString();
    }
  }
};
</script>
