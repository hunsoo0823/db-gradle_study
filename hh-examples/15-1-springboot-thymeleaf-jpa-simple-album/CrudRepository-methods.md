

The CrudRepository interface includes the following methods:

    findById    finds the entity in the database with the specified ID.
    findAll     returns all entities of the repository type from the database.
    findAllById passed a collection of IDs, this method returns all entities for those IDs.

    save        persists an entity to the database (create or update).
    saveAll     saves a collection of entities to the database.

    delete      deletes the specified entity.
    deleteById  deletes the entity with the specified ID.
    deleteAll   deletes all entities managed by the repository.

    count       returns the number of entities that are in the database.
    existsById  returns true if an entity with the specified ID exists in the database.

