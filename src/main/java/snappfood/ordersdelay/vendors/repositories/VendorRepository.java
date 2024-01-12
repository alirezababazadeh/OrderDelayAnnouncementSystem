package snappfood.ordersdelay.vendors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snappfood.ordersdelay.vendors.entities.VendorEntity;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Long> {
}
