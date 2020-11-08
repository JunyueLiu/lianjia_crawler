package dataPreProcessing.greatCircleDistance;

public class DistanceCalculate {
	public static double earth_Distance(double lon_0,double lat_0,double lon_1,double lat_1) {
		//111.12×〖𝒄𝒐𝒔〗^(−𝟏)  [sin(〖𝒍𝒂𝒕〗_𝒊)sin(〖𝒍𝒂𝒕〗_𝒋)+
		//cos(〖𝒍𝒂𝒕〗_𝒊)cos(〖𝒍𝒂𝒕〗_𝒋)cos(〖𝒍𝒐𝒏〗_𝒊−〖𝒍𝒐𝒏〗_𝒋)]

		double dis = 111.12*Math.acos(Math.sin(lat_0)*Math.sin(lat_1)
				+Math.cos(lat_0)*Math.cos(lat_1)*Math.cos(lon_0-lon_1));
		
		return dis;
	}

}
