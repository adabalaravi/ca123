package com.accenture.ams.db.bean;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;



/**
 * The persistent class for the CONTENT database table.
 * 
 * @author BEA Workshop
 */
public class Content  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Long contentId;
	//private PriceCategory priceCategoryId;
	private String title;
	private Long episode;
	private String episodeTitle;
	private long duration;
	private java.sql.Timestamp contractEnd;
	private java.sql.Timestamp contractStart;
	
	private String genre;
	private long categoryId;
	private String isHd;
	private String isEncrypted;
	private Long entitlementId;
	private String externalContentId;
	private java.sql.Timestamp lastBroadcastDate;
	private String broadcastChannelName;
	private String shortDescription;
	private String longDescription;

	private String contentYear;
	private String directors;
	private String actors;
	private String anchors;
	private String authors;
	private String language;
	private String season;
	private String eventPlace;
	private String country;

	private String isAdult;
	private String additionalData00;
	private String additionalData01;
	private String additionalData02;
	private String type;
	private String pcLevel;
	private String isRecommended;

//	private java.sql.Timestamp creationDate;



	

	
	
	
	
	

	
	

	
//	private java.sql.Timestamp updateDate;
	//private java.util.Set<ContentPlatform> contentPlatforms;
	private java.util.Set<ContentExtension> contentExtensions;
	//private java.util.Set<RelContentExtra> relContentExtras=new HashSet<RelContentExtra>(0);
	//private java.util.Set<ContentCategory> contentCategories;	
	//private java.util.Set<RelContentExtRat> relContentExtRats=new HashSet<RelContentExtRat>(0);
	//private java.util.Set<MostWanted> mostWanteds = new HashSet<MostWanted>(0);
	//private Set<RelContentExtra> relContentExtrasForExtraId = new HashSet<RelContentExtra>(0);
	  
/*	public Set<RelContentExtra> getRelContentExtrasForExtraId() {
		return relContentExtrasForExtraId;
	}
	public void setRelContentExtrasForExtraId(
			Set<RelContentExtra> relContentExtrasForExtraId) {
		this.relContentExtrasForExtraId = relContentExtrasForExtraId;
	}
*/
	private String urlPictures;
	private String packageId;
//	
	public Content() {
		 super();
    }
	 public Content(Long contentId,  String title,
			Long episode, String episodeTitle, long duration,
			Timestamp contractEnd, Timestamp contractStart, String genre,
			long categoryId, String isHd, String isEncrypted,
			Long entitlementId, String externalContentId,
			Timestamp lastBroadcastDate, String broadcastChannelName,
			String shortDescription, String longDescription,
			String contentYear, String directors, String actors,
			String anchors, String authors, String language, String season,
			String eventPlace, String country, String isAdult,
			String additionalData00, String additionalData01,
			String additionalData02, String type, String pcLevel,
			String isRecommended) {
	 super();
        this.contentId = contentId;
        this.title = title;
		this.episode = episode;
		this.episodeTitle = episodeTitle;
        this.duration = duration;
		this.contractEnd = contractEnd;
        this.contractStart = contractStart;
		this.genre = genre;
        this.categoryId = categoryId;
		this.isHd = isHd;
        this.isEncrypted = isEncrypted;
		this.entitlementId = entitlementId;
		this.externalContentId = externalContentId;
		this.lastBroadcastDate = lastBroadcastDate;
		this.broadcastChannelName = broadcastChannelName;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.contentYear = contentYear;
		this.directors = directors;
		this.actors = actors;
		this.anchors = anchors;
		this.authors = authors;
		this.language = language;
		this.season = season;
		this.eventPlace = eventPlace;
		this.country = country;
		this.isAdult = isAdult;
		this.additionalData00 = additionalData00;
		this.additionalData01 = additionalData01;
		this.additionalData02 = additionalData02;
		this.type = type;
		this.pcLevel = pcLevel;
        this.isRecommended = isRecommended;
    }
	 
	 
	 
	public Content(Long contentId,  String title,
			Long episode, String episodeTitle, long duration,
			Timestamp contractEnd, Timestamp contractStart, String genre,
			long categoryId, String isHd, String isEncrypted,
			Long entitlementId, String externalContentId,
			Timestamp lastBroadcastDate, String broadcastChannelName,
			String shortDescription, String longDescription,
			String contentYear, String directors, String actors,
			String anchors, String authors, String language, String season,
			String eventPlace, String country, String isAdult,
			String additionalData00, String additionalData01,
			String additionalData02, String type, String pcLevel,
			String isRecommended, 
			Set<ContentExtension> contentExtensions,
			Set<RelContentExtRat> relContentExtRats) {
		super();
       this.contentId = contentId;
       this.title = title;
       this.episode = episode;
       this.episodeTitle = episodeTitle;
       this.duration = duration;
		this.contractEnd = contractEnd;
       this.contractStart = contractStart;
       this.genre = genre;
       this.categoryId = categoryId;
		this.isHd = isHd;
       this.isEncrypted = isEncrypted;
       this.entitlementId = entitlementId;
       this.externalContentId = externalContentId;
       this.lastBroadcastDate = lastBroadcastDate;
       this.broadcastChannelName = broadcastChannelName;
       this.shortDescription = shortDescription;
       this.longDescription = longDescription;
       this.contentYear = contentYear;
       this.directors = directors;
       this.actors = actors;
       this.anchors = anchors;
       this.authors = authors;
       this.language = language;
       this.season = season;
       this.eventPlace = eventPlace;
       this.country = country;
		this.isAdult = isAdult;
       this.additionalData00 = additionalData00;
       this.additionalData01 = additionalData01;
       this.additionalData02 = additionalData02;
		this.type = type;
		this.pcLevel = pcLevel;
       this.isRecommended = isRecommended;
		this.contentExtensions = contentExtensions;
    }
	public Content(Long contentId,  String title,
			Long episode, String episodeTitle, long duration,
			Timestamp contractEnd, Timestamp contractStart, String genre,
			long categoryId, String isHd, String isEncrypted,
			Long entitlementId, String externalContentId,
			Timestamp lastBroadcastDate, String broadcastChannelName,
			String shortDescription, String longDescription,
			String contentYear, String directors, String actors,
			String anchors, String authors, String language, String season,
			String eventPlace, String country, String isAdult,
			String additionalData00, String additionalData01,
			String additionalData02, String type, String pcLevel,
			String isRecommended, 
			Set<RelContentExtRat> relContentExtRats) {
		super();
       this.contentId = contentId;
       this.title = title;
       this.episode = episode;
       this.episodeTitle = episodeTitle;
       this.duration = duration;
		this.contractEnd = contractEnd;
       this.contractStart = contractStart;
       this.genre = genre;
       this.categoryId = categoryId;
		this.isHd = isHd;
       this.isEncrypted = isEncrypted;
       this.entitlementId = entitlementId;
       this.externalContentId = externalContentId;
       this.lastBroadcastDate = lastBroadcastDate;
       this.broadcastChannelName = broadcastChannelName;
       this.shortDescription = shortDescription;
       this.longDescription = longDescription;
       this.contentYear = contentYear;
       this.directors = directors;
       this.actors = actors;
       this.anchors = anchors;
       this.authors = authors;
       this.language = language;
       this.season = season;
       this.eventPlace = eventPlace;
       this.country = country;
		this.isAdult = isAdult;
       this.additionalData00 = additionalData00;
       this.additionalData01 = additionalData01;
       this.additionalData02 = additionalData02;
		this.type = type;
		this.pcLevel = pcLevel;
       this.isRecommended = isRecommended;

    
    }
	 
/*	public Content(Long contentId,  String title,
			Long episode, String episodeTitle, long duration,
			Timestamp contractEnd, Timestamp contractStart, String genre,
			long categoryId, String isHd, String isEncrypted,
			Long entitlementId, String externalContentId,
			Timestamp lastBroadcastDate, String broadcastChannelName,
			String shortDescription, String longDescription,
			String contentYear, String directors, String actors,
			String anchors, String authors, String language, String season,
			String eventPlace, String country, String isAdult,
			String additionalData00, String additionalData01,
			String additionalData02, String type, String pcLevel,
			String isRecommended, 
			Set<ContentExtension> contentExtensions,
			Set<RelContentExtRat> relContentExtRats
) {
		super();
		this.contentId = contentId;
		this.title = title;
		this.episode = episode;
		this.episodeTitle = episodeTitle;
		this.duration = duration;
		this.contractEnd = contractEnd;
		this.contractStart = contractStart;
		this.genre = genre;
		this.categoryId = categoryId;
		this.isHd = isHd;
		this.isEncrypted = isEncrypted;
		this.entitlementId = entitlementId;
		this.externalContentId = externalContentId;
		this.lastBroadcastDate = lastBroadcastDate;
		this.broadcastChannelName = broadcastChannelName;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.contentYear = contentYear;
		this.directors = directors;
		this.actors = actors;
		this.anchors = anchors;
		this.authors = authors;
		this.language = language;
		this.season = season;
		this.eventPlace = eventPlace;
		this.country = country;
		this.isAdult = isAdult;
		this.additionalData00 = additionalData00;
		this.additionalData01 = additionalData01;
		this.additionalData02 = additionalData02;
		this.type = type;
		this.pcLevel = pcLevel;
		this.isRecommended = isRecommended;
		this.contentExtensions = contentExtensions;
	
	}
*/	public Content(Long contentId,  String title,
			Long episode, String episodeTitle, long duration,
			Timestamp contractEnd, Timestamp contractStart, String genre,
			long categoryId, String isHd, String isEncrypted,
			Long entitlementId, String externalContentId,
			Timestamp lastBroadcastDate, String broadcastChannelName,
			String shortDescription, String longDescription,
			String contentYear, String directors, String actors,
			String anchors, String authors, String language, String season,
			String eventPlace, String country, String isAdult,
			String additionalData00, String additionalData01,
			String additionalData02, String type, String pcLevel,
			String isRecommended, 
			Set<ContentExtension> contentExtensions,
			Set<RelContentExtRat> relContentExtRats,
			String urlPictures, String packageId) {
		super();
		this.contentId = contentId;
		this.title = title;
		this.episode = episode;
		this.episodeTitle = episodeTitle;
		this.duration = duration;
		this.contractEnd = contractEnd;
		this.contractStart = contractStart;
		this.genre = genre;
		this.categoryId = categoryId;
		this.isHd = isHd;
		this.isEncrypted = isEncrypted;
		this.entitlementId = entitlementId;
		this.externalContentId = externalContentId;
		this.lastBroadcastDate = lastBroadcastDate;
		this.broadcastChannelName = broadcastChannelName;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.contentYear = contentYear;
		this.directors = directors;
		this.actors = actors;
		this.anchors = anchors;
		this.authors = authors;
		this.language = language;
		this.season = season;
		this.eventPlace = eventPlace;
		this.country = country;
		this.isAdult = isAdult;
		this.additionalData00 = additionalData00;
		this.additionalData01 = additionalData01;
		this.additionalData02 = additionalData02;
		this.type = type;
		this.pcLevel = pcLevel;
		this.isRecommended = isRecommended;
		this.contentExtensions = contentExtensions;
		
	}
/*	public java.util.Set<RelContentExtRat> getRelContentExtRats() {
		return relContentExtRats;
	}

	public void setRelContentExtRats(
			java.util.Set<RelContentExtRat> relContentExtRats) {
		this.relContentExtRats = relContentExtRats;
	}
*/
	public String getIsAdult() {
		return isAdult;
	}

	public void setIsAdult(String isAdult) {
		this.isAdult = isAdult;
	}




	

	
	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public Long getContentId() {
		return this.contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public String getActors() {
		return this.actors;
	}
	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getAdditionalData00() {
		return this.additionalData00;
	}
	public void setAdditionalData00(String additionalData00) {
		this.additionalData00 = additionalData00;
	}

	public String getAdditionalData01() {
		return this.additionalData01;
	}
	public void setAdditionalData01(String additionalData01) {
		this.additionalData01 = additionalData01;
	}

	public String getAdditionalData02() {
		return this.additionalData02;
	}
	public void setAdditionalData02(String additionalData02) {
		this.additionalData02 = additionalData02;
	}

	public String getAnchors() {
		return this.anchors;
	}
	public void setAnchors(String anchors) {
		this.anchors = anchors;
	}

	public String getAuthors() {
		return this.authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getBroadcastChannelName() {
		return this.broadcastChannelName;
	}
	public void setBroadcastChannelName(String broadcastChannelName) {
		this.broadcastChannelName = broadcastChannelName;
	}

	@Deprecated
	public long getCategoryId() {
		return this.categoryId;
	}
	@Deprecated
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getContentType() {
		return this.type;
	}

	public void setContentType(String contentType) {
		this.type = contentType;
	}

	public String getContentYear() {
		return this.contentYear;
	}
	public void setContentYear(String contentYear) {
		this.contentYear = contentYear;
	}

	public java.sql.Timestamp getContractEnd() {
		return this.contractEnd;
	}
	public void setContractEnd(java.sql.Timestamp contractEnd) {
		this.contractEnd = contractEnd;
	}

	public java.sql.Timestamp getContractStart() {
		return this.contractStart;
	}
	public void setContractStart(java.sql.Timestamp contractStart) {
		this.contractStart = contractStart;
	}

	public String getCountry() {
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public String getDirectors() {
		return this.directors;
	}
	public void setDirectors(String directors) {
		this.directors = directors;
	}

	public long getDuration() {
		return this.duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}

	public Long getEntitlementId() {
		return this.entitlementId;
	}
	public void setEntitlementId(Long entitlementId) {
		this.entitlementId = entitlementId;
	}

	public Long getEpisode() {
		return this.episode;
	}
	public void setEpisode(Long episode) {
		this.episode = episode;
	}

	public String getEpisodeTitle() {
		return this.episodeTitle;
	}
	public void setEpisodeTitle(String episodeTitle) {
		this.episodeTitle = episodeTitle;
	}

	public String getEventPlace() {
		return this.eventPlace;
	}
	public void setEventPlace(String eventPlace) {
		this.eventPlace = eventPlace;
	}

	public String getExternalContentId() {
		return this.externalContentId;
	}
	public void setExternalContentId(String externalContentId) {
		this.externalContentId = externalContentId;
	}

	public String getGenre() {
		return this.genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getIsEncrypted() {
		return this.isEncrypted;
	}
	public void setIsEncrypted(String isEncrypted) {
		this.isEncrypted = isEncrypted.trim();
	}

	public String getIsHd() {
		return this.isHd;
	}
	public void setIsHd(String isHd) {
		this.isHd = isHd.trim();
	}

	public String getLanguage() {
		return this.language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

	public java.sql.Timestamp getLastBroadcastDate() {
		return this.lastBroadcastDate;
	}
	public void setLastBroadcastDate(java.sql.Timestamp lastBroadcastDate) {
		this.lastBroadcastDate = lastBroadcastDate;
	}

	public String getLongDescription() {
		return this.longDescription;
	}
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getPackageId() {
		return this.packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPcLevel() {
		return this.pcLevel;
	}
	public void setPcLevel(String pcLevel) {
		this.pcLevel = pcLevel.trim();
	}

/*	public PriceCategory getPriceCategoryId() {
		return this.priceCategoryId;
	}
	public void setPriceCategoryId(PriceCategory priceCategoryId) {
		this.priceCategoryId = priceCategoryId;
	}
*/
	public String getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(String isRecommended) {
		this.isRecommended = isRecommended;
	}

	public String getSeason() {
		return this.season;
	}
	public void setSeason(String season) {
		this.season = season;
	}

	public String getShortDescription() {
		return this.shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	//bi-directional many-to-one association to ExtraContentPlatform
/*	public java.util.Set<ContentPlatform> getContentPlatforms() {
		return this.contentPlatforms;
	}
	public void setContentPlatforms(java.util.Set<ContentPlatform> contentPlatforms) {
		this.contentPlatforms = contentPlatforms;
	}
*/
	//bi-directional many-to-one association to RelContentExtraTest
	

	//bi-directional many-to-one association to RelContentExtraTest
	
	

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Content)) {
			return false;
		}
		Content castOther = (Content)other;
		return new EqualsBuilder()
			.append(this.getContentId(), castOther.getContentId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getContentId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("contentId", getContentId())
			.toString();
	}
	
	 
/*	public java.util.Set<RelContentExtra> getRelContentExtras() {
		return this.relContentExtras;
	}

	public void setRelContentExtras(java.util.Set<RelContentExtra> relContentExtras) {
		this.relContentExtras = relContentExtras;
	}
*/

	public String getUrlPictures() {
		return urlPictures;
	}

	public void setUrlPictures(String urlPictures) {
		this.urlPictures = urlPictures;
	}
	 
	//bi-directional many-to-one association to ContentExtension
	public java.util.Set<ContentExtension> getContentExtensions() {
		return this.contentExtensions;
	}
	public void setContentExtensions(java.util.Set<ContentExtension> contentExtensions) {
		this.contentExtensions = contentExtensions;
	}
	//bi-directional many-to-one association to Category
/*	public java.util.Set<ContentCategory> getContentCategories() {
		return contentCategories;
	}

	public void setContentCategories(
			java.util.Set<ContentCategory> contentCategories) {
		this.contentCategories = contentCategories;
	}
	
	public void setMostWanteds(java.util.Set<MostWanted> mostWanteds) {
		this.mostWanteds = mostWanteds;
	}

	public java.util.Set<MostWanted> getMostWanteds() {
		return mostWanteds;
	}
*/	
	
}
