package com.oli.node.dao

import com.oli.node.state.Session
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionRepository : CrudRepository<Session, String> {}