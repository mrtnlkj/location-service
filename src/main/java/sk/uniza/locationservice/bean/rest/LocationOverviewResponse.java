package sk.uniza.locationservice.bean.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import sk.uniza.locationservice.bean.Location;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class LocationOverviewResponse extends OverviewResponse<Location> {
}
