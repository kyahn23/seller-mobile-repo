<template>
  <div v-cloak>
    <q-tabs
      background-color="white"
      indicator-color="primary"
      dense
      align="justify"
    >
      <q-route-tab to="/product/SKT" label="SKT"/>
      <q-route-tab to="/product/KT" label="KT"/>
      <q-route-tab to="/product/LGU" label="LGU"/>
    </q-tabs>
    <div id="top" class="q-pt-md">
      <div class="q-px-md q-mb-xs">
        <span class="text-subtitle1 text-weight-bold">
          {{ carrier }} 상품현황
        </span>
      </div>
      <q-separator/>
      <div class="q-py-sm q-pt-md q-px-md row items-center justify-between">
        <div class="col-2">
          제조사
        </div>
        <q-select
          class="col"
          outlined
          dense
          v-model="selectMaker"
          :options="makerList"
          map-options
          emit-value
        >
        </q-select>
      </div>
      <div class="q-py-sm q-px-md row items-center justify-between">
        <div class="col-2">
          모델명
        </div>
        <q-select
          class="col"
          outlined
          dense
          v-model="selectModel"
          :options="phoneList"
          emit-value
          map-options
        >
        </q-select>
      </div>
      <div class="q-py-sm q-pb-md q-px-md row items-center justify-between">
        <q-btn
          unelevated
          rounded
          padding="sm"
          color="primary"
          class="full-width"
          @click="getSellList"
        >
          검색
        </q-btn>
      </div>
      <q-separator/>
      <div class="row">
        <q-btn
          class="col-4 no-border-radius"
          unelevated
          :color="setThisColor('moveCarrList')"
          :text-color="setThisText('moveCarrList')"
          @click="setThisList('moveCarrList')"
          label="번호이동"/>
        <q-btn
          class="col-4 no-border-radius"
          unelevated
          :color="setThisColor('chgDeviceList')"
          :text-color="setThisText('chgDeviceList')"
          @click="setThisList('chgDeviceList')"
          label="기기변경"/>
        <q-btn
          class="col-4 no-border-radius"
          unelevated
          :color="setThisColor('newSignUpList')"
          :text-color="setThisText('newSignUpList')"
          @click="setThisList('newSignUpList')"
          label="신규가입"/>
      </div>
      <q-separator/>
    </div>
    <div v-if="thisList.length === 0">
      <q-item class="q-py-md">
        <q-item-section class="text-grey-5">
          <q-item-label class="self-center">
            <q-icon name="error" size="xl"/>
          </q-item-label>
          <q-item-label v-if="searchInit" class="q-pt-md self-center text-subtitle1">
            모델을 선택해 주세요.
          </q-item-label>
          <q-item-label v-else class="q-pt-md self-center text-subtitle1">
            판매중인 정책이 없습니다.
          </q-item-label>
        </q-item-section>
      </q-item>
    </div>
    <div v-else v-for="(thisOne, index) in thisList" :key="index">
      <q-expansion-item
        v-model="thisOne.acco"
        group="thisAccordion"
        :duration="1"
        :label="thisOne.pnMntRtNm"
        :caption="'월 ' + thisOne.pnMntAmt + '원'"
        style="font-weight: bold;"
      >
        <q-card style="border-top: 1px solid rgba(0, 0, 0, 0.12);
            font-weight: normal;">
          <q-card-section>
            <div class="q-mb-xs"></div>
            <div class="q-pa-xs">
              <table style="width: 100%; border-spacing: 0px;" class="text-center">
                <tr style="height: 2.5rem;">
                  <td style="width: 30%; border-top: 2px solid lightgrey;"/>
                  <th style="width: 30%; border-top: 2px solid lightgrey;"><span>공시지원</span></th>
                  <th style="width: 30%; border-top: 2px solid lightgrey;">선택약정</th>
                </tr>
                <tr style="height: 2.5rem;">
                  <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">출고가</th>
                  <td style="width: 30%; border-top: 2px solid lightgrey;">
                    <span></span>{{ Number(thisOne.pnMsrp).toLocaleString() }}
                  </td>
                  <td style="width: 30%; border-top: 2px solid lightgrey;">
                    <span>{{ Number(thisOne.pnMsrp).toLocaleString() }}</span></td>
                </tr>
                <tr style="height: 2.5rem;">
                  <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">공시지원금</th>
                  <td style="width: 30%; border-top: 2px solid lightgrey;">
                    <span>{{ Number(thisOne.pnOfclSubsd).toLocaleString() }}</span></td>
                  <td style="width: 30%; border-top: 2px solid lightgrey;"><span>-</span></td>
                </tr>
                <tr style="height: 2.5rem;">
                  <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">추가지원금</th>
                  <td style="width: 30%; border-top: 2px solid lightgrey;">
                    <span>{{ Number(thisOne.pnExtSubsdDv).toLocaleString() }}</span></td>
                  <td style="width: 30%; border-top: 2px solid lightgrey;">
                    <span>{{ Number(thisOne.pnExtSubsdRt).toLocaleString() }}</span></td>
                </tr>
                <tr style="height: 2.5rem;">
                  <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">할부원금</th>
                  <td style="width: 30%; border-top: 2px solid lightgrey;">
                    <span>{{
                        Number(thisOne.pnMsrp - thisOne.pnOfclSubsd - thisOne.pnExtSubsdDv).toLocaleString()
                      }}</span>
                  </td>
                  <td style="width: 30%; border-top: 2px solid lightgrey;">
                    <span>{{ Number(thisOne.pnMsrp - thisOne.pnExtSubsdRt).toLocaleString() }}</span></td>
                </tr>
                <tr style="height: 2.5rem;">
                  <th style="width: 30%; border-top: 2px solid lightgrey;" class="bg-blue-1">부가서비스</th>
                  <td style="width: 30%; border-top: 2px solid lightgrey;">
                    <span>{{ pnExtServYn(thisOne.pnExtServYn) }}</span></td>
                  <td style="width: 30%; border-top: 2px solid lightgrey;">
                    <span>{{ pnExtServYn(thisOne.pnExtServYn) }}</span></td>
                </tr>
                <tr style="height: 4rem;">
                  <th style="width: 30%; border-top: 2px solid lightgrey; border-bottom: 2px solid lightgrey"
                      class="bg-blue-1">부가서비스 정책
                  </th>
                  <td style="width: 30%; border-top: 2px solid lightgrey; border-bottom: 2px solid lightgrey"
                      colspan="2">
                    <span v-if="!thisOne.pnEtc">-</span>
                    <span v-else>{{ thisOne.pnEtc }}</span>
                  </td>
                </tr>
              </table>
            </div>
          </q-card-section>
        </q-card>
      </q-expansion-item>
      <q-separator/>
    </div>
  </div>
</template>

<script>
import {scroll} from "quasar";

const {getScrollTarget, setScrollPosition} = scroll;

export default {
  name: "PageProductView",
  props: {
    carrier: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      searchInit: true,
      selectMaker: "all",
      makerList: [
        {label: "선택", value: "all"},
        {label: "삼성", value: "SAM"},
        {label: "애플", value: "APP"},
        {label: "LG", value: "LGE"},
        {label: "샤오미", value: "XIA"},
        {label: "기타", value: "ETC"}
      ],
      selectModel: {label: "선택", pnMdlNo: "all"},
      phoneList: [],
      thisSelected: null,
      thisList: [],
      moveCarrList: [],
      chgDeviceList: [],
      newSignUpList: []
    };
  },
  watch: {
    carrier(val) {
      this.selectMaker = "all"
      this.selectModel = {label: "선택", pnMdlNo: "all"}
      this.phoneList = []
      this.searchInit = true
      this.thisSelected = "moveCarrList"
      this.thisList = []
      this.moveCarrList = []
      this.chgDeviceList = []
      this.newSignUpList = []
    },

    thisSelected: function (newValue, oldValue) {
      if (newValue === "moveCarrList") {
        this.thisList = this.moveCarrList;
      } else if (newValue === "chgDeviceList") {
        this.thisList = this.chgDeviceList;
      } else if (newValue === "newSignUpList") {
        this.thisList = this.newSignUpList;
      }
      if (this.thisList.length > 0){
        for (let idx in this.thisList){
          this.thisList[idx].acco = false
        }
      }
    },
    selectMaker(newValue, oldValue) {
      if (newValue !== oldValue) {
        this.selectModel = {
          label: "선택", pnMdlNo: "all"
        }
        this.phoneList = []
        this.getPhoneList(newValue)
      }
    }
  },
  mounted() {
    // this.$store.commit("setLoading", {isLoading: true});
    // this.getBoard(this.page);
    this.setThisList("moveCarrList");
  },
  methods: {
    getPhoneList(val) {
      let param = {
        pnCarr: this.carrier.substr(0, 1),
        pnMkr: val
      }
      this.$cf.call(
        process.env.API + "/product/getDeviceList",
        param,
        this.getPhoneListCB,
        true
      )
    },
    getPhoneListCB(response) {
      const obj = {
        label: "선택",
        pnMdlNo: "all"
      };
      this.phoneList.push(obj);
      for (let n in response.phoneList) {
        this.phoneList.push(response.phoneList[n]);
      }
    },
    getSellList() {
      let param = {
        pnCarr: this.carrier,
        pnNetType: this.selectModel.pnNetType,
        pnStor: this.selectModel.pnStor,
        pnMdlNo: this.selectModel.pnMdlNo
      }
      this.$cf.call(
        process.env.API + "/product/getUseMntByDevice",
        param,
        this.getSellListCB,
        true
      )
    },
    getSellListCB(response) {
      this.searchInit = false
      if (response.moveCarrList.length > 0){
        for (let idx in response.moveCarrList){
          response.moveCarrList[idx].acco = false
        }
      }
      if (response.chgDeviceList.length > 0){
        for (let idx in response.chgDeviceList){
          response.chgDeviceList[idx].acco = false
        }
      }
      if (response.newSignUpList.length > 0){
        for (let idx in response.newSignUpList){
          response.newSignUpList[idx].acco = false
        }
      }
      this.moveCarrList = response.moveCarrList
      this.chgDeviceList = response.chgDeviceList
      this.newSignUpList = response.newSignUpList
      if (this.thisSelected === "moveCarrList") {
        this.thisList = this.moveCarrList;
      } else if (this.thisSelected === "chgDeviceList") {
        this.thisList = this.chgDeviceList;
      } else if (this.thisSelected === "newSignUpList") {
        this.thisList = this.newSignUpList;
      }
    },
    setThisList(selected) {
      this.thisSelected = selected;
    },
    setThisColor(selected) {
      if (selected === this.thisSelected) {
        return "primary";
      } else {
        return "white";
      }
    },
    setThisText(selected) {
      if (selected === this.thisSelected) {
        return "white";
      } else {
        return "black";
      }
    },
    pnExtServYn(val) {
      return val === 'Y' ? '있음' : '없음'
    }
  },
  computed: {}
};
</script>

<style scoped></style>
