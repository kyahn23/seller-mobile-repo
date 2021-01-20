<template>
  <div class="q-pt-md">
    <div class="row">
      <div class="q-px-md text-subtitle1 text-weight-medium q-mb-xs">
        상담상세정보
      </div>
    </div>
    <q-separator/>
    <q-list>
      <q-item class="q-py-md">
        <q-item-section avatar>
          <q-avatar square size="5em">
            <img :src="$cf.imagePath(cs.pnImg)">
          </q-avatar>
        </q-item-section>
        <q-item-section>
          <div class="full-width text-caption text-weight-medium">
            <div class="row q-pl-sm">
              <span class="float-left" style="width: 30%">고객정보</span>
              <span style="width: 70%">{{ cs.clMbrNm }} / {{ cs.clMbrPnNo }}</span>
            </div>
            <div class="row q-pl-sm">
              <span class="float-left" style="width: 30%">통신사</span>
              <span style="width: 70%">{{ cs.mntCarr }}</span>
            </div>
            <div class="row q-pl-sm">
              <span class="float-left" style="width: 30%">가입유형</span>
              <span style="width: 70%">{{ cs.pnRegDis }}</span>
            </div>
            <div class="row q-pl-sm">
              <span class="float-left" style="width: 30%">요금제</span>
              <span style="width: 70%">{{ cs.pnMntRtNm }}</span>
            </div>
            <div class="row q-pl-sm" v-if="cs.bnMbrNm">
              <span class="float-left" style="width: 30%">등록자</span>
              <span style="width: 70%">{{ cs.bnMbrNm }}</span>
            </div>
          </div>
        </q-item-section>
      </q-item>
      <q-separator/>
      <div class="q-pa-md text-grey-5">
        <div class="row q-py-xs text-caption rounded-borders justify-evenly" style="border: solid 1px #2d90f5">
          <span :class="cs.callStCd === 'R' ? 'text-primary text-weight-medium' : ''">상담접수</span>
          <span :class="cs.callStCd === 'P' ? 'text-primary text-weight-medium' : ''">방문예정</span>
          <span :class="cs.callStCd === 'T' ? 'text-primary text-weight-medium' : ''">개통완료</span>
          <span :class="cs.blkYn === 0 && (cs.callStCd === 'E'|| cs.callStCd === 'C') ? 'text-primary text-weight-medium' : ''">미개통</span>
          <span :class="cs.blkYn === 1 && (cs.callStCd === 'E'|| cs.callStCd === 'C') ? 'text-primary text-weight-medium' : ''">블랙고객</span>
        </div>
      </div>
      <div class="row">
        <div class="q-px-md text-subtitle1 text-weight-medium">
          상담 접수 상세정보
        </div>
      </div>
      <div class="q-pa-sm">
        <table style="width: 100%; border-spacing: 0px;" class="text-center">
          <tr style="height: 2.5rem;">
            <td style="width: 30%; border-top: 2px solid lightgrey;"/>
            <th style="width: 30%; border-top: 2px solid lightgrey; border-left: 1px solid lightgrey"><span>접수 시</span></th>
            <th style="width: 30%; border-top: 2px solid lightgrey; border-left: 1px solid lightgrey">현재</th>
          </tr>

          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-3" colspan="3">공시지원</th>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">출고가</th>
            <td style="width: 30%; border-top: 2px solid lightgrey; border-right: 1px solid lightgrey"><span>{{
                Number(cs.pnMsrp).toLocaleString()
              }}</span></td>
            <td style="width: 30%; border-top: 2px solid lightgrey;">
              <span>{{ Number(currentPolicy.pnMsrp).toLocaleString() }}</span></td>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">공시지원금</th>
            <td style="width: 30%; border-top: 2px solid lightgrey; border-right: 1px solid lightgrey">
              <span>{{ Number(cs.oldOfclSubsd).toLocaleString() }}</span>
            </td>
            <td style="width: 30%; border-top: 2px solid lightgrey;">
              <span>{{ Number(currentPolicy.pnOfclSubsd).toLocaleString() }}</span>
            </td>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">공시 추가지원금</th>
            <td style="width: 30%; border-top: 2px solid lightgrey; border-right: 1px solid lightgrey">
              <span>{{ Number(cs.oldExtSubsdDv).toLocaleString() }}</span>
            </td>
            <td style="width: 30%; border-top: 2px solid lightgrey;">
              <span>{{ Number(currentPolicy.pnExtSubsdDv).toLocaleString() }}</span>
            </td>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">할부원금</th>
            <td style="width: 30%; border-top: 2px solid lightgrey; border-right: 1px solid lightgrey">
              <span>{{ Number(cs.pnMsrp - cs.oldOfclSubsd - cs.oldExtSubsdDv).toLocaleString() }}</span>
            </td>
            <td style="width: 30%; border-top: 2px solid lightgrey;">
              <span>{{
                  Number(currentPolicy.pnMsrp - currentPolicy.pnOfclSubsd - currentPolicy.pnExtSubsdDv).toLocaleString()
                }}</span>
            </td>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-3" colspan="3">선택약정</th>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">출고가</th>
            <td style="width: 30%; border-top: 2px solid lightgrey; border-right: 1px solid lightgrey"><span>{{
                Number(cs.pnMsrp).toLocaleString()
              }}</span></td>
            <td style="width: 30%; border-top: 2px solid lightgrey;">
              <span>{{ Number(currentPolicy.pnMsrp).toLocaleString() }}</span></td>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">선약 추가지원금</th>
            <td style="width: 30%; border-top: 2px solid lightgrey; border-right: 1px solid lightgrey">
              <span>{{ Number(cs.oldExtSubsdRt).toLocaleString() }}</span>
            </td>
            <td style="width: 30%; border-top: 2px solid lightgrey;">
              <span>{{ Number(currentPolicy.pnExtSubsdRt).toLocaleString() }}</span>
            </td>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">할부원금</th>
            <td style="width: 30%; border-top: 2px solid lightgrey; border-right: 1px solid lightgrey">
              <span>{{ Number(cs.pnMsrp - cs.oldExtSubsdRt).toLocaleString() }}</span>
            </td>
            <td style="width: 30%; border-top: 2px solid lightgrey;">
              <span>{{ Number(currentPolicy.pnMsrp - currentPolicy.pnExtSubsdRt).toLocaleString() }}</span></td>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-3" colspan="3">비고</th>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">부가서비스</th>
            <td style="width: 30%; border-top: 2px solid lightgrey; border-right: 1px solid lightgrey"><span>{{ pnExtServYn(cs.oldExtServYn) }}</span>
            </td>
            <td style="width: 30%; border-top: 2px solid lightgrey;">
              <span>{{ pnExtServYn(currentPolicy.pnExtServYn) }}</span></td>
          </tr>
          <tr style="height: 4rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey; border-bottom: 2px solid lightgrey"
                class="bg-blue-1">고객요청사항
            </th>
            <td style="width: 30%; border-top: 2px solid lightgrey; border-bottom: 2px solid lightgrey" colspan="2">
              <span>{{ cs.dealReq }}</span></td>
          </tr>
        </table>
      </div>
      <div class="row">
        <div class="q-px-md text-subtitle1 text-weight-medium">
          상담 결과 정보
        </div>
      </div>
      <div class="q-pa-sm">
        <table style="width: 100%; border-spacing: 0px;" class="text-center">
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">방문예정일시</th>
            <td style="width: 60%; border-top: 2px solid lightgrey;">
              <span v-if="cs.visitDt">{{ cs.visitDt }}</span>
              <span v-else> - </span>
            </td>
          </tr>
          <tr style="height: 4rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey; border-bottom: 2px solid lightgrey"
                class="bg-blue-1" v-if="cs.blkYn === 0">상담내용
            </th>
            <th style="width: 30%; border-top: 2px solid lightgrey; border-bottom: 2px solid lightgrey"
                class="bg-blue-1" v-else>취소사유
            </th>
            <td style="width: 60%; border-top: 2px solid lightgrey; border-bottom: 2px solid lightgrey" colspan="2">
              <span v-if="cs.blkYn === 0 && cs.callCont">{{ cs.callCont }}</span>
              <span v-else-if="cs.blkYn === 1 && cs.cnclCmnt" > {{cs.cnclCmnt}} </span>
              <span v-else> - </span>
            </td>
          </tr>
        </table>
      </div>
      <div class="text-center q-mb-md">
        <q-btn
          unelevated
          color="primary"
          label="목록"
          size="md"
          class="q-px-lg q-py-xs"
          @click="goToList"
        />
      </div>
    </q-list>


  </div>
</template>

<script>
export default {
  name: "Status Detail",
  data() {
    return {
      cs: {},
      currentPolicy: {}
    }
  },
  mounted() {
    this.cs = this.$store.getters.cs
    console.log(this.cs)
    this.$store.commit("setCs", {cs: {}})

    console.log(this.cs.blkYn)


    this.getCurrentPolicy()
  },
  methods: {
    getCurrentPolicy() {
      let param = {
        pnMdlNo: this.cs.pnMdlNo,
        pnStor: this.cs.pnStor,
        pnRegDis: this.cs.pnRegDisCd,
        pnMntRtNo: this.cs.pnMntRtNo
      }
      this.$cf.call(
        process.env.API + "/status/getCurrPolicy",
        param,
        this.getCurrentPolicyCB,
        false
      )
    },
    getCurrentPolicyCB(response) {
      console.log("현재정책")
      console.log(response)
      this.currentPolicy = response
    },
    pnExtServYn(val) {
      return val === 'Y' ? '있음' : '없음'
    },
    goToList() {
      this.$router.push({path: "/status"});
    },
  }

};
</script>
