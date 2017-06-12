package com.accenture.sdp.csmfe.webservices.utils;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csm.dto.responses.SdpDeviceBrandResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceChannelResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceCounterResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceModelResponseDto;
import com.accenture.sdp.csm.dto.responses.SdpDeviceResponseDto;
import com.accenture.sdp.csmfe.webservices.response.device.DeviceChannelType;
import com.accenture.sdp.csmfe.webservices.response.device.DeviceCounterType;
import com.accenture.sdp.csmfe.webservices.response.device.DeviceType;
import com.accenture.sdp.csmfe.webservices.response.device.SearchAllDeviceBrandsResp;
import com.accenture.sdp.csmfe.webservices.response.device.SearchDeviceModelsByBrandResp;

public class DeviceBeanConverter extends BaseBeanConverter {

	public static SearchAllDeviceBrandsResp.Brands.Brand convertBrand(SdpDeviceBrandResponseDto dto) {
		if (dto == null) {
			return null;
		}
		SearchAllDeviceBrandsResp.Brands.Brand info = new SearchAllDeviceBrandsResp.Brands.Brand();
		info.setBrandName(dto.getBrandName());
		info.setBrandId(dto.getBrandId());
		info.setIsBlacklisted(dto.getIsBlacklisted());
		info.setIsWhitelisted(dto.getIsWhitelisted());
		info.setBlacklistReason(dto.getBlacklistReason());
		return info;
	}

	public static List<SearchAllDeviceBrandsResp.Brands.Brand> convertBrands(List<SdpDeviceBrandResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<SearchAllDeviceBrandsResp.Brands.Brand> infos = new ArrayList<SearchAllDeviceBrandsResp.Brands.Brand>();
		for (SdpDeviceBrandResponseDto dto : dtos) {
			infos.add(convertBrand(dto));
		}
		return infos;
	}

	public static SearchDeviceModelsByBrandResp.Models.Model convertModel(SdpDeviceModelResponseDto dto) {
		if (dto == null) {
			return null;
		}
		SearchDeviceModelsByBrandResp.Models.Model info = new SearchDeviceModelsByBrandResp.Models.Model();
		info.setModelId(dto.getModelId());
		info.setModelName(dto.getModelName());
		info.setIsBlacklisted(dto.getIsBlacklisted());
		info.setIsWhitelisted(dto.getIsWhitelisted());
		info.setBlacklistReason(dto.getBlacklistReason());
		return info;
	}

	public static List<SearchDeviceModelsByBrandResp.Models.Model> convertModels(List<SdpDeviceModelResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<SearchDeviceModelsByBrandResp.Models.Model> infos = new ArrayList<SearchDeviceModelsByBrandResp.Models.Model>();
		for (SdpDeviceModelResponseDto dto : dtos) {
			infos.add(convertModel(dto));
		}
		return infos;
	}

	public static DeviceChannelType convertChannel(SdpDeviceChannelResponseDto dto) {
		if (dto == null) {
			return null;
		}
		DeviceChannelType info = new DeviceChannelType();
		info.setDeviceChannelId(dto.getDeviceChannelId());
		info.setDeviceChannelName(dto.getDeviceChannelName());
		info.setIsBlacklisted(dto.getIsBlacklisted());
		info.setIsWhitelisted(dto.getIsWhitelisted());
		info.setBlacklistReason(dto.getBlacklistReason());
		info.setPortable(dto.getPortable());
		return info;
	}

	public static List<DeviceChannelType> convertChannels(List<SdpDeviceChannelResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<DeviceChannelType> infos = new ArrayList<DeviceChannelType>();
		for (SdpDeviceChannelResponseDto dto : dtos) {
			infos.add(convertChannel(dto));
		}
		return infos;
	}

	public static DeviceType convertDevice(SdpDeviceResponseDto dto) {
		if (dto == null) {
			return null;
		}
		DeviceType info = new DeviceType();
		convertBaseInfo(dto, info);
		info.setBlacklistReason(dto.getBlacklistReason());
		info.setDeviceAlias(dto.getDeviceAlias());
		info.setDeviceBrand(dto.getDeviceBrand());
		info.setDeviceChannel(dto.getDeviceChannel());
		info.setDeviceModel(dto.getDeviceModel());
		info.setDeviceUUID(dto.getDeviceUUID());
		info.setDeviceUUIDType(dto.getDeviceUUIDType());
		info.setIsBlacklisted(dto.isBlacklisted());
		info.setIsPaired(dto.isPaired());
		info.setIsWhitelisted(dto.isWhitelisted());
		info.setLastFruitionDate(dto.getLastFruitionDate());
		info.setPartyId(dto.getPartyId());
		info.setStatus(dto.getStatus());
		return info;
	}

	public static List<DeviceType> convertDevices(List<SdpDeviceResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<DeviceType> infos = new ArrayList<DeviceType>();
		for (SdpDeviceResponseDto dto : dtos) {
			infos.add(convertDevice(dto));
		}
		return infos;
	}
	
	public static DeviceCounterType convertDeviceCounter(SdpDeviceCounterResponseDto dto) {
		if (dto == null) {
			return null;
		}
		DeviceCounterType info = new DeviceCounterType();
		info.setDeviceChannelId(dto.getDeviceChannelId());
		info.setDeviceChannelName(dto.getDeviceChannelName());
		info.setRegisteredDevices(dto.getRegisteredDevices());
		return info;
	}

	public static List<DeviceCounterType> convertDeviceCounters(List<SdpDeviceCounterResponseDto> dtos) {
		if (dtos == null) {
			return null;
		}
		List<DeviceCounterType> infos = new ArrayList<DeviceCounterType>();
		for (SdpDeviceCounterResponseDto dto : dtos) {
			infos.add(convertDeviceCounter(dto));
		}
		return infos;
	}

}