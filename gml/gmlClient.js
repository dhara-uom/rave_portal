/**
 * Created with IntelliJ IDEA.
 * User: sameera
 * Date: 7/3/13
 * Time: 11:29 PM
 * To change this template use File | Settings | File Templates.
 */

function gmlClient(){
    var lon = 5;
    var lat = 40;
    var zoom = 5;
    var map, layer;

    this.loadGML = function(URL){
        map=new OpenLayers.Layer.Vector("GML", {
            protocol: new OpenLayers.Protocol.HTTP({
                url: URL,
                format: new OpenLayers.Format.GML.v3()
            }),
            strategies: [new OpenLayers.Strategy.Fixed()]
        });

        map.setVisibility(true);

        return map;
    }



}