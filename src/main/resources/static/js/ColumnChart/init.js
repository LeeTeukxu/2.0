function indexFn() {
  require(['jquery', 'echarts'], function($, echarts) {
    console.log(echarts)
      $(function() {
          techbuildData();
          // busbuildData();
          // clientbuildData();
      });

      function techbuildData() {
          //定义数据结构
          var titles = mini.get('type').getText() + "-" + mini.get('state').getText() + "统计";
          var datas = {
              'colors': TechColor,
              'xAxis': TechName,
              'legend':TechColumn,
              'list': [
                  {
                      'title': titles,
                      'series': TechNameOfNum,
                      'elid': 'tech',
                      'total': '总和：' + Total,
                      'maxs': Max
                  }
              ]
          };
          for (var i = 0; i < datas['list'].length; i++) {
              techcanvasEcharts(datas, i);
          }
      }

      function techcanvasEcharts(json, index) {
          var obj = json['list'][index];
          var myChats = echarts.init(document.getElementById(obj['elid']));
          var option = {
              title: {
                  text: obj['title'],
                  // subtext: index==0?'数据来自投票结果而时时变化':''  //只有第一个要副标题
                  //主标题文本超链接
                  //link: 'http://www.xxxxxxxxxx',
                  subtext: obj['total'],
                  subtextStyle: {
                      color: 'black',
                      fontSize: 16
                  }
              },
              color: TechColor,
              tooltip: {
                  trigger: 'axis',
                  axisPointer: {
                      type: 'shadow'
                  },
                  formatter: function (params) {
                      var Name = params[0].name;
                      var valueHtml = "";
                      var valueTotal = 0;
                      for (var i=0;i<params.length;i++) {
                            valueHtml += "<span>" + params[i].seriesName + "：" + params[i].value + "</span></br>";
                            valueTotal += params[i].value
                      }
                      var result = Name + "</br>" + valueHtml + "总计：" + valueTotal;
                      return result;
                  }

              },
              toolbox: {
                  //显示策略，可选为：true（显示） | false（隐藏），默认值为true
                  show: false,
                  //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
                  y: 'center',
                  feature: {
                      //saveAsImage，保存图片（IE8-不支持）,图片类型默认为'png'
                      saveAsImage: { show: true }
                  }
              },
              legend: {
                  left: "40%",
                  data: TechColumn
              },
              grid: {
                  left: '0%',
                  right: '0%',
                  bottom: '3%',
                  containLabel: true,
              },
              xAxis: [{
                  min: 0,
                  //坐标轴类型，横轴默认为类目型'category'
                  type: 'category',
                  data: json['xAxis']
              }],
              yAxis: [{
                  min: 0,
                  max: obj['maxs'],
                  //坐标轴类型，纵轴默认为数值型'value'
                  type: 'value'
              }],
              series: (function(){
                  var arr=[];
                  for (var i = 0; i < obj['series'].length; i++) {
                      var thisobj={
                          name: json['legend'][i],
                          type: 'bar',
                          barWidth: '5%',
                      };
                      thisobj.data=obj['series'][i];
                      arr.push(thisobj)
                  }
                  return arr
              })()
          };
          //为echarts对象加载数据
          myChats.setOption(option, true);
          window.onresize = function () {
              myChats.resize();
          }
      }

      // function busbuildData() {
      //     //定义数据结构
      //     var datas = {
      //         'colors': BusColor,
      //         'xAxis': BusName,
      //         'legend':BusColumn,
      //         'list': [
      //             {
      //                 'title': '4.您对正在使用的品牌质量的满意程度',
      //                 'series': BusNameOfNum,
      //                 'elid': 'bus'
      //             }
      //         ]
      //     };
      //     for (var i = 0; i < datas['list'].length; i++) {
      //         buscanvasEcharts(datas, i);
      //     }
      // }
      //
      // function buscanvasEcharts(json, index) {
      //     var obj = json['list'][index];
      //     var myChats = echarts.init(document.getElementById(obj['elid']));
      //     var option = {
      //         title: {
      //             text: obj['title'],
      //             // subtext: index==0?'数据来自投票结果而时时变化':''  //只有第一个要副标题
      //             //主标题文本超链接
      //             //link: 'http://www.xxxxxxxxxx',
      //             subtext: '数据来自投票结果而时时变化'
      //         },
      //         color: BusColor,
      //         tooltip: {
      //             trigger: 'axis',
      //             axisPointer: {
      //                 type: 'shadow'
      //             },
      //             formatter: function (params) {
      //                 var Name = params[0].name;
      //                 var valueHtml = "";
      //                 var valueTotal = 0;
      //                 for (var i=0;i<params.length;i++) {
      //                     valueHtml += "<span>" + params[i].seriesName + "：" + params[i].value + "</span></br>";
      //                     valueTotal += params[i].value
      //                 }
      //                 var result = Name + "</br>" + valueHtml + "总计：" + valueTotal;
      //                 return result;
      //             }
      //
      //         },
      //         toolbox: {
      //             //显示策略，可选为：true（显示） | false（隐藏），默认值为true
      //             show: true,
      //             //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
      //             y: 'center',
      //             feature: {
      //                 //saveAsImage，保存图片（IE8-不支持）,图片类型默认为'png'
      //                 saveAsImage: { show: true }
      //             }
      //         },
      //         legend: {
      //             left: "70%",
      //             data: BusColumn
      //         },
      //         grid: {
      //             left: '3%',
      //             right: '10%',
      //             bottom: '3%',
      //             containLabel: true,
      //         },
      //         xAxis: [{
      //             min: 0,
      //             //坐标轴类型，横轴默认为类目型'category'
      //             type: 'category',
      //             data: json['xAxis']
      //         }],
      //         yAxis: [{
      //             min: 0,
      //             max: 300,
      //             //坐标轴类型，纵轴默认为数值型'value'
      //             type: 'value'
      //         }],
      //         series: (function(){
      //             var arr=[];
      //             for (var i = 0; i < obj['series'].length; i++) {
      //                 var thisobj={
      //                     name: json['legend'][i],
      //                     type: 'bar',
      //                     barWidth: '15%',
      //                 };
      //                 thisobj.data=obj['series'][i];
      //                 arr.push(thisobj)
      //             }
      //             return arr
      //         })()
      //     };
      //     //为echarts对象加载数据
      //     myChats.setOption(option, true);
      // }
      //
      // function clientbuildData() {
      //     //定义数据结构
      //     var datas = {
      //         'colors': ClientColor,
      //         'xAxis': ClientName,
      //         'legend':ClientColumn,
      //         'list': [
      //             {
      //                 'title': '4.您对正在使用的品牌质量的满意程度',
      //                 'series': ClientNameOfNum,
      //                 'elid': 'client'
      //             }
      //         ]
      //     };
      //     for (var i = 0; i < datas['list'].length; i++) {
      //         clientcanvasEcharts(datas, i);
      //     }
      // }
      //
      // function clientcanvasEcharts(json, index) {
      //     var obj = json['list'][index];
      //     var myChats = echarts.init(document.getElementById(obj['elid']));
      //     var option = {
      //         title: {
      //             text: obj['title'],
      //             // subtext: index==0?'数据来自投票结果而时时变化':''  //只有第一个要副标题
      //             //主标题文本超链接
      //             //link: 'http://www.xxxxxxxxxx',
      //             subtext: '数据来自投票结果而时时变化'
      //         },
      //         color: ClientColor,
      //         tooltip: {
      //             trigger: 'axis',
      //             axisPointer: {
      //                 type: 'shadow'
      //             },
      //             formatter: function (params) {
      //                 var Name = params[0].name;
      //                 var valueHtml = "";
      //                 var valueTotal = 0;
      //                 for (var i=0;i<params.length;i++) {
      //                     valueHtml += "<span>" + params[i].seriesName + "：" + params[i].value + "</span></br>";
      //                     valueTotal += params[i].value
      //                 }
      //                 var result = Name + "</br>" + valueHtml + "总计：" + valueTotal;
      //                 return result;
      //             }
      //
      //         },
      //         toolbox: {
      //             //显示策略，可选为：true（显示） | false（隐藏），默认值为true
      //             show: true,
      //             //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
      //             y: 'center',
      //             feature: {
      //                 //saveAsImage，保存图片（IE8-不支持）,图片类型默认为'png'
      //                 saveAsImage: { show: true }
      //             }
      //         },
      //         legend: {
      //             left: "70%",
      //             data: ClientColumn
      //         },
      //         grid: {
      //             left: '3%',
      //             right: '10%',
      //             bottom: '3%',
      //             containLabel: true,
      //         },
      //         xAxis: [{
      //             min: 0,
      //             //坐标轴类型，横轴默认为类目型'category'
      //             type: 'category',
      //             data: json['xAxis']
      //         }],
      //         yAxis: [{
      //             min: 0,
      //             max: 300,
      //             //坐标轴类型，纵轴默认为数值型'value'
      //             type: 'value'
      //         }],
      //         series: (function(){
      //             var arr=[];
      //             for (var i = 0; i < obj['series'].length; i++) {
      //                 var thisobj={
      //                     name: json['legend'][i],
      //                     type: 'bar',
      //                     barWidth: '15%',
      //                 };
      //                 thisobj.data=obj['series'][i];
      //                 arr.push(thisobj)
      //             }
      //             return arr
      //         })()
      //     };
      //     //为echarts对象加载数据
      //     myChats.setOption(option, true);
      // }

  });
}

require(['config'], indexFn);