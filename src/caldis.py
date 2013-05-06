
import math

EARTH_RADIUS=6378137;
def calDis(lat, lng, bearing, distance):
        """
        lat1 = (double) Math.toRadians(lat1);
        lng1 = (double) Math.toRadians(lng1);
        bearing = (double) Math.toRadians(bearing);
        double r = distance/EARTH_RADIUS;
        double[] point2 = new double[2];
        double lat2 = Math.asin(Math.sin(lat1)*Math.cos(r) + 
                Math.cos(lat1)*Math.sin(r)*Math.cos(bearing));
        double lng2 = (lng1 + 
        Math.atan2(Math.sin(bearing)*Math.sin(r)*Math.cos(lat1),
                Math.cos(r)-Math.sin(lat1)*Math.sin(lat2)));
        point2[0] = Math.toDegrees(lat2);
        point2[1] = Math.toDegrees(lng2);
        return point2;
        """
        lat1 = math.radians((float)(lat))
        lng1 = math.radians((float)(lng))
        bearing = math.radians((float)(bearing))
        r =  (float)(distance)/EARTH_RADIUS
        lat2 = math.asin(math.sin(lat1) * math.cos(r) + math.cos(lat1) * math.sin(r) * math.cos(bearing))
        lng2 = (lng1 + math.atan2(math.sin(bearing) * math.sin(r) * math.cos(lat1), math.cos(r)-math.sin(lat1)*math.sin(lat2)))
        return math.degrees(lat2),math.degrees(lng2)


#print calDis('11','22','33','4')