
in HSQLDB 2.2.9

  left is keyword!

  Solution 1

    use @Embeddable, @Column in StereoVolume.java

  Solution 2

    StreoVolume.left --> StreoVolume.leftvol
    StreoVolume.right --> StreoVolume.rightvol

  Solution 3: in Track.java

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "left", column = @Column(name = "VOL_LEFT")),
        @AttributeOverride(name = "right", column = @Column(name = "VOL_RIGHT"))
    })

