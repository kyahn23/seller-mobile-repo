<template>
  <div>
    <q-tabs
      background-color="white"
      indicator-color="primary"
      dense
      align="left"
    >
      <q-route-tab to="/product/SKT" label="SKT" />
      <q-route-tab to="/product/KT" label="KT" />
      <q-route-tab to="/product/LGU" label="LGU" />
    </q-tabs>
    <div id="top" class="q-pt-md">
      <div class="q-px-md q-mb-xs">
        <span class="text-subtitle1 text-weight-bold">
          {{ carrier }} 상품현황
        </span>
      </div>
      <q-separator />
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
          :options="modelList"
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
        >
          검색
        </q-btn>
      </div>
      <q-separator />
      <div class="q-py-sm q-px-md row items-center justify-between">
        <q-btn
          unelevated
          :color="setThisColor('a')"
          :text-color="setThisText('a')"
          @click="setThisList('a')"
        >
          번호이동
        </q-btn>
        <q-btn
          unelevated
          :color="setThisColor('b')"
          :text-color="setThisText('b')"
          @click="setThisList('b')"
        >
          기기변경
        </q-btn>
        <q-btn
          unelevated
          :color="setThisColor('c')"
          :text-color="setThisText('c')"
          @click="setThisList('c')"
        >
          신규가입
        </q-btn>
      </div>
      <q-separator />
    </div>
    <div v-for="(thisOne, index) in thisList" :key="index">
      <q-expansion-item
        group="thisAccordion"
        :label="thisOne.pnMntRtNm"
        :caption="'월 ' + thisOne.pnMntAmt + '원'"
        style="font-weight: bold;"
      >
        <q-card
          style="border-top: 1px solid rgba(0, 0, 0, 0.12);
            font-weight: normal;"
        >
          <q-card-section>
            <div class="q-mb-xs">{{ thisOne.testText }}</div>
          </q-card-section>
        </q-card>
      </q-expansion-item>
      <q-separator />
    </div>
    <q-list>
      <q-item clickable class="q-pt-none q-pb-sm" @click="scrollTop">
        <q-item-section>
          <q-item-label class="self-center">
            <q-icon name="expand_less" size="md" />
          </q-item-label>
          <q-item-label class="self-center text-caption">
            맨 위로 돌아가기
          </q-item-label>
        </q-item-section>
      </q-item>
    </q-list>
  </div>
</template>

<script>
import { scroll } from "quasar";

const { getScrollTarget, setScrollPosition } = scroll;

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
      selectMaker: "SAM",
      makerList: [
        { label: "삼성", value: "SAM" },
        { label: "애플", value: "APL" },
        { label: "LG", value: "LG" },
        { label: "기타", value: "ETC" }
      ],
      selectModel: "phone01",
      modelList: [
        { label: "폰01", value: "phone01" },
        { label: "폰02", value: "phone02" },
        { label: "폰03", value: "phone03" },
        { label: "폰04", value: "phone04" }
      ],
      thisSelected: null,
      thisList: [],
      aList: [
        {
          pnMntRtNm: "번이요금제01",
          pnMntAmt: "100000",
          testText: "번이요금제01 내용"
        },
        {
          pnMntRtNm: "번이요금제02",
          pnMntAmt: "100000",
          testText: "번이요금제02 내용"
        },
        {
          pnMntRtNm: "번이요금제03",
          pnMntAmt: "100000",
          testText: "번이요금제03 내용"
        }
      ],
      bList: [
        {
          pnMntRtNm: "기변요금제01",
          pnMntAmt: "200000",
          testText: "기변요금제01 내용"
        },
        {
          pnMntRtNm: "기변요금제02",
          pnMntAmt: "200000",
          testText: "기변요금제02 내용"
        },
        {
          pnMntRtNm: "기변요금제03",
          pnMntAmt: "200000",
          testText: "기변요금제03 내용"
        }
      ],
      cList: [
        {
          pnMntRtNm: "신규요금제01",
          pnMntAmt: "300000",
          testText: "신규요금제01 내용"
        },
        {
          pnMntRtNm: "신규요금제02",
          pnMntAmt: "300000",
          testText: "신규요금제02 내용"
        },
        {
          pnMntRtNm: "신규요금제03",
          pnMntAmt: "300000",
          testText: "신규요금제03 내용"
        }
      ]
    };
  },
  watch: {
    thisSelected: function(newValue, oldValue) {
      if (newValue === "a") {
        this.thisList = this.aList;
      } else if (newValue === "b") {
        this.thisList = this.bList;
      } else if (newValue === "c") {
        this.thisList = this.cList;
      }
    }
  },
  mounted() {
    // this.$store.commit("setLoading", {isLoading: true});
    // this.getBoard(this.page);
    this.setThisList("a");
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
    }
  }
};
</script>

<style scoped></style>
