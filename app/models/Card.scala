package models

import java.util.UUID

/**
 * Created by Lapanti on 04/08/15.
 */
case class Card (
                id: UUID,
                story: String,
                description: String
                  )