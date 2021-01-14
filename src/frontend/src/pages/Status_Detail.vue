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
          <q-avatar square size="4.5em">
            <img :src="'images/phone_search.png'">
          </q-avatar>
        </q-item-section>
        <q-item-section>
          <div class="full-width text-caption text-weight-medium">
            <div class="row">
              <span class="float-left" style="width: 30%">고객정보</span><span style="width: 70%">손흥민 / 010-1111-2222</span>
            </div>
            <div class="row">
              <span class="float-left" style="width: 30%">통신사</span><span style="width: 70%">SKT</span>
            </div>
            <div class="row">
              <span class="float-left" style="width: 30%">가입유형</span><span style="width: 70%">번호이동</span>
            </div>
            <div class="row">
              <span class="float-left" style="width: 30%">요금제</span><span style="width: 70%">5GX 플래티넘</span>
            </div>
            <div class="row">
              <span class="float-left" style="width: 30%">등록자</span><span style="width: 70%">직원1</span>
            </div>
          </div>
        </q-item-section>
      </q-item>
      <q-separator/>
      <div class="q-pa-md text-grey-5">
        <div class="row q-py-xs text-caption rounded-borders justify-evenly" style="border: solid 1px #2d90f5">
          <span>상담접수</span>
          <span class="text-primary text-weight-medium">방문예정</span>
          <span>개통완료</span>
          <span>미개통</span>
          <span>블랙고객</span>
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
            <th style="width: 30%; border-top: 2px solid lightgrey;"><span>접수 시</span></th>
            <th style="width: 30%; border-top: 2px solid lightgrey;">현재</th>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">출고가</th>
            <td style="width: 30%; border-top: 2px solid lightgrey;"><span>1,000,000</span></td>
            <td style="width: 30%; border-top: 2px solid lightgrey;"><span>1,000,000</span></td>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">공시지원금</th>
            <td style="width: 30%; border-top: 2px solid lightgrey;"><span>180,000</span></td>
            <td style="width: 30%; border-top: 2px solid lightgrey;"><span>180,000</span></td>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">추가지원금</th>
            <td style="width: 30%; border-top: 2px solid lightgrey;"><span>230,000</span></td>
            <td style="width: 30%; border-top: 2px solid lightgrey;"><span>230,000</span></td>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">실구매가</th>
            <td style="width: 30%; border-top: 2px solid lightgrey;"><span>540,000</span></td>
            <td style="width: 30%; border-top: 2px solid lightgrey;"><span>540,000</span></td>
          </tr>
          <tr style="height: 2.5rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">부가서비스</th>
            <td style="width: 30%; border-top: 2px solid lightgrey;"><span>없음</span></td>
            <td style="width: 30%; border-top: 2px solid lightgrey;"><span>있음</span></td>
          </tr>
          <tr style="height: 4rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey; border-bottom: 2px solid lightgrey"
                class="bg-blue-1">고객요청사항
            </th>
            <td style="width: 30%; border-top: 2px solid lightgrey; border-bottom: 2px solid lightgrey" colspan="2">
              <span>고객이요청한 내용이 출력됨</span></td>
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
            <td style="width: 60%; border-top: 2px solid lightgrey;"><span>2021-01-12 18:00</span></td>
          </tr>
          <tr style="height: 4rem;">
            <th style="width: 30%; border-top: 2px solid lightgrey; border-bottom: 2px solid lightgrey"
                class="bg-blue-1">상담내용
            </th>
            <td style="width: 60%; border-top: 2px solid lightgrey; border-bottom: 2px solid lightgrey" colspan="2">
              <span>회선 180일 유지<br>부가서비스 90일후 변경가능</span></td>
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
  name: "Status",
  data() {
    return {
      pageInit: false,
      pageInfo: {},
      kwd: "",
      selectOpt: "clientNm",
      optList: [
        {label: "고객명", value: "clientNm"},
        {label: "연락처", value: "clientNum"}
      ],
      dealList: [
        {mntCarr: "SKT", pnRegDis: "번호이동", pnMntRtNm: "요금제이름"}
      ],
      sales: true
    }
  },
  methods: {
    /** 맨 위로 돌아가기 이벤트 */
    scrollTop() {
      const ele = document.getElementById("top");
      const target = getScrollTarget(ele);
      const offset = ele.offsetTop - 1000;
      const duration = 200;
      setScrollPosition(target, offset, duration);
    },
    /** infinite scroll 이벤트 */
    onScrollLoad(index, done) {
      setTimeout(() => {
        if (index === 1) {
          this.getList(index + "");
          done();
        } else if (this.pageInfo.hasNextPage) {
          this.getList(this.pageInfo.nextPage + "");
          done();
        }
      }, 2000);
    },
    getList(page) {
      // this.$cf.call(
      //   process.env.API + "/api/deal/list",
      //   {
      //     email: this.currentUser,
      //     page: page
      //   },
      //   this.getListCB,
      //   true
      // );
    },
    /** list 콜백 함수 */
    getListCB(response) {
      // this.pageInfo = response.pageInfo;
      // for (let n in response.dealList) {
      //   this.dealList.push(response.dealList[n]);
      // }
      // this.pageInit = true;
    },
    saleChg() {
      this.sales = !this.sales
    },
    goToList() {
      this.$router.push({path: "/status"});
    }
  }

};
</script>
