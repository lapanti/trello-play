package models

import java.util.UUID

/**
 * Created by Lapanti on 04/08/15.
 */
case class Category (
                    id: UUID,
                    name: String,
                    cards: List[Card]
                      )
