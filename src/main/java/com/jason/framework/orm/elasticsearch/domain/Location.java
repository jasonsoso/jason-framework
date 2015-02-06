package com.jason.framework.orm.elasticsearch.domain;

import com.jason.framework.mapper.JsonMapper;

/**
 * 基于位置geo
 * @author Jason
 * @date 2014-12-12
 */
public class Location {
	    private double lat;
	    private double lon;

	    public Location(double lat, double lon) {
	        this.lat = lat;
	        this.lon = lon;
	    }

	    @Override
		public String toString() {
			return JsonMapper.toJsonString(this);
		}

		public double getLat() {
	        return lat;
	    }

	    public Location setLat(double lat) {
	        this.lat = lat;
	        return this;
	    }

	    public double getLon() {
	        return lon;
	    }

	    public Location setLon(double lon) {
	        this.lon = lon;
	        return this;
	    }
}
