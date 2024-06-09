class TestOrderOfBlocksClass {
    private val name: String
    constructor(name: String) {
        println("Constructor")
        this.name = name
    }

    init {
        println("Init block")
    }
}