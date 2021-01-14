<template>
  <q-layout class="column flex-center q-pb-xl">
    <q-img
      class="q-pt-xl q-pb-md"
      src="images/pentas_logo_320.png"
      style="width: 60%; max-width: 240px;"
      contain
    />
    <p class="text-subtitle1 text-weight-medium">판매업체 전용 App</p>
    <q-form class="flex column fit" ref="loginForm" name="loginForm" :action="rootCtx + '/loginProc'" method="post">
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
        v-model="userPw"
        label="비밀번호"
        rounded
        outlined
        name="userPw"
        id="userPw"
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
      <div class="flex column q-px-lg q-pt-lg q-gutter-y-md fit">
        <q-btn
          unelevated
          rounded
          color="primary"
          label="로그인"
          type="button"
          @click="onClick"
        />
      </div>
    </q-form>
    <q-footer class="text-center justify-center row bg-white">
      <q-tabs class="text-grey-7"
              align="center">
        ©2021 PENTAS. All rights reserved.
      </q-tabs>
    </q-footer>
  </q-layout>
</template>

<script>
import sha256 from "crypto-js/sha256";

export default {
  name: 'Login',
  data() {
    return {
      isPwd: true,
      userId: "",
      userPw: "",
      rootCtx: process.env.API
    }
  },
  methods:{
    onClick() {
      if (!this.$cf.isEmpty(this.userId) && !this.$cf.isEmpty(this.userPw)) {
        document.loginForm.submit()
      }
    },
  }
}
</script>
