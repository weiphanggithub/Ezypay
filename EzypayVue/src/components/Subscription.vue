<template>
  <div class="vdp">
    <h1>{{ msg }}</h1>
    <form v-on:submit="sub" action="#" method="post">

      <!-- Amount -->
      <div>
        <label class="label">Amount: </label>
        <input id="amount" type="number">
      </div><br>

      <!-- Type -->
      <div>
        <label class="label">Type: </label>
        <select id="type" @change="methodSelect()">
          <option selected="selected" value="">Please select one</option>
          <option>DAILY</option>
          <option>WEEKLY</option>
          <option>MONTHLY</option>
        </select>
      </div><br>

      <!-- Days -->
      <div style="display: none;" id="weeklyList">
        <label class="label">Days: </label>
        <select id="weeklyValue">
          <option v-for="val in weeklyList" :value="val.code" :key="val.code">{{val.value}}</option>
        </select>
        <br>
      </div>
      <div style="display: none;" id="monthlyList">
        <label class="label">Days: </label>
        <select id="monthlyValue">
          <option v-for="val in monthlyList" :value="val.value" :key="val.value">{{val.value}}</option>
        </select>
        <br>
      </div>

      <div>
        <label class="label">Date Range:</label>
        <datepicker id="start" format="dd/MM/yyyy"></datepicker> to <datepicker id="end" format="dd/MM/yyyy"></datepicker>
      </div><br>

      <button @click.prevent="submit()">Create</button><br><br>
      <button @click.prevent="display()">Show</button>
      <button @click.prevent="clear()">clear</button>

      <div id="list"></div>
          
    </form>
  </div>
</template>

<script src="jquery-3.4.1.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js">
</script>

<script>
import Datepicker from 'vuejs-datepicker';
import axios from 'axios';

export default {
  name: 'Subcription',
  props: {
    msg: String,
    html: String
  },
  components: {
    Datepicker
  },
  data() {
    return {
      weeklyList: [
      { code: 2, value:'Monday' },
      { code: 3, value:'Tuesday' },
      { code: 4, value:'Wednesday' },
      { code: 5, value:'Thursday' },
      { code: 6, value:'Friday' },
      { code: 7, value:'Saturday' },
      { code: 1, value:'Sunday' }
    ],
    monthlyList: [
      { value:'1' },{ value:'2' },{ value:'3' }, { value:'4' },{ value:'5' },
      { value:'6' },{ value:'7' },{ value:'8' }, { value:'9' },{ value:'10' },
      { value:'11' },{ value:'12' },{ value:'13' }, { value:'14' },{ value:'15' },
      { value:'16' },{ value:'17' },{ value:'18' }, { value:'19' },{ value:'20' },
      { value:'21' },{ value:'22' },{ value:'23' }, { value:'24' },{ value:'25' },
      { value:'26' },{ value:'27' },{ value:'28' }, { value:'29' },{ value:'30' },
      { value:'31' }
    ],
    result : ''
    }
  },
  methods: {
    submit(){
      var start = document.getElementById("start").value;
      var end = document.getElementById("end").value;
      var date1 = new Date(start.split('/')[2],start.split('/')[1]-1,start.split('/')[0]);
      var date2 = new Date(end.split('/')[2],end.split('/')[1]-1,end.split('/')[0]);
      var daysBetween = (date2.getTime() - date1.getTime()) / 86400000;

      if (daysBetween > 90) {
        alert("Date range greater than 90 days");
        return false;
      }

      var amount = document.getElementById("amount").value;
      var type = document.getElementById("type").value;
      var days = '';
      if (type=='WEEKLY'){
        days = document.getElementById("weeklyValue").value;
      } else if (type=='MONTHLY'){
        days = document.getElementById("monthlyValue").value;
      } else if (type=='DAILY'){
        days = 'NA';
        this.type = "Daily";
      }

      if (amount.length <= 0){
        alert("Amount is required.");
        return false;
      } else if (type.length <= 0) {
        alert("Type is required.");
        return false;
      } else if (days.length <= 0) {
        alert("Days is required.");
        return false;
      } else if (start.length <= 0) {
        alert("Start date is required.");
        return false;
      } else if (end.length <= 0) {
        alert("End date is required.");
        return false;
      }


      var body = '';
      body += "{";
      body += '"amount":' + amount + ",";
      body += '"type":' + '"' + type + '"' + ',';
      body += '"days":' + '"' + days + '"' + ',';
      body += '"start":' + '"' + start + '"' + ',';
      body += '"end":' + '"' + end + '"';
      body += "}";

      const insert = 'http://weiphang.com/insert';

      axios.post(insert,body)
      .then(function (response) {
         alert("data :" + response.data);
      })
      .catch(function (error) {
        alert(error);
      });
      
    },
    methodSelect(){
      
      var value = document.getElementById("type").value;
      var w = document.getElementById("weeklyList");
      var m = document.getElementById("monthlyList");
      if (value=='WEEKLY'){
        w.style.display = "block";
        m.style.display = "none";
      } else if (value=='MONTHLY'){
        w.style.display = "none";
        m.style.display = "block";
      } else {
        w.style.display = "none";
        m.style.display = "none";
      }
    },
    display(){
      const url = 'http://weiphang.com/display';
        axios.post(url)
        .then(function (response) {
          document.getElementById("list").innerHTML = response.data;
      })
      .catch(function (error) {
        alert(error);
      });
    },
    clear(){
      const url = 'http://weiphang.com/clear';
        axios.post(url)
        .then(function (response) {
          alert(response.data);
      })
      .catch(function (error) {
        alert(error);
      });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
.vdp {
position: relative;
text-align: left;
box-sizing: border-box;

}
.table {
padding: 2;
border: 1;
}
    
</style>
	